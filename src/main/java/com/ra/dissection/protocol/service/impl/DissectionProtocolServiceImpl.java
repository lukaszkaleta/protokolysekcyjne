package com.ra.dissection.protocol.service.impl;

import com.google.common.collect.*;
import com.ra.dissection.protocol.dao.protocol.*;
import com.ra.dissection.protocol.dao.UserSearchMapper;
import com.ra.dissection.protocol.dao.settings.DescriptionPointSourceMapper;
import com.ra.dissection.protocol.dao.settings.DissectionDiagnoseSourceMapper;
import com.ra.dissection.protocol.dao.settings.DissectionDiagnoseSourceOptionMapper;
import com.ra.dissection.protocol.domain.common.OrderSwitch;
import com.ra.dissection.protocol.domain.common.Range;
import com.ra.dissection.protocol.domain.protocol.*;
import com.ra.dissection.protocol.domain.report.ReportStatus;
import com.ra.dissection.protocol.domain.search.UserSearch;
import com.ra.dissection.protocol.domain.settings.*;
import com.ra.dissection.protocol.service.DissectionDiagnoseSourceService;
import com.ra.dissection.protocol.service.DissectionProtocolService;
import com.ra.dissection.protocol.service.ReportService;
import com.ra.dissection.protocol.service.exception.ProtocolNotExistsException;
import com.ra.dissection.protocol.service.support.CollectionSupport;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author lukaszkaleta
 * @since 01.05.13 12:44
 */
@Service
@SuppressWarnings("unused")
public class DissectionProtocolServiceImpl implements DissectionProtocolService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DissectionDiagnoseSourceService dissectionDiagnoseSourceService;

    @Autowired
    private DissectionProtocolMapper dissectionProtocolMapper;

    @Autowired
    private UserSearchMapper userSearchMapper;

    @Autowired
    private DeathStoryEntryMapper deathStoryEntryMapper;

    @Autowired
    private DescriptionPointMapper descriptionPointMapper;

    @Autowired
    private DissectionDiagnoseMapper dissectionDiagnoseMapper;

    @Autowired
    private DescriptionPointSourceMapper descriptionPointSourceMapper;

    @Autowired
    private HospitalWardEntryMapper hospitalWardEntryMapper;

    @Autowired
    private HistopathologicalExaminationMapper histopathologicalExaminationMapper;

    @Autowired
    private DissectionDiagnoseOptionMapper dissectionDiagnoseOptionMapper;

    @Autowired
    private DissectionDiagnoseSourceMapper dissectionDiagnoseSourceMapper;

    @Autowired
    private DissectionDiagnoseSourceOptionMapper dissectionDiagnoseSourceOptionMapper;

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private ReportService reportService;

    @Autowired
    private CollectionSupport collectionSupport;

    @Override
    @Transactional
    public DissectionProtocol create(DissectionProtocol dissectionProtocol) {
        dissectionProtocol.setCreationDate(new Date());
        dissectionProtocolMapper.insert(dissectionProtocol);
        long id = dissectionProtocol.getId();
        for (DeathStoryEntry deathStoryEntry : dissectionProtocol.getBasicData().getDeathStory().getStoryEntries()) {
            deathStoryEntry.setDissectionProtocolId(id);
            deathStoryEntryMapper.insertDeathStoryEntry(deathStoryEntry);
        }
        DissectionProtocolCategory.Name category = dissectionProtocol.getCategory();

        // Hospital Ward Entries
        for (HospitalWardEntry hospitalWardEntry : dissectionProtocol.getBasicData().getDeathStory().getHospitalWardEntries()) {
            hospitalWardEntry.setDissectionProtocolId(id);
            hospitalWardEntryMapper.insertHospitalWardEntry(hospitalWardEntry);
        }

        // Default description points
        createDefaultDescriptionPoints(category, id);

        // Create empty report
        reportMapper.insertEmptyReport(id);

        return loadBasicData(id);
    }

    @Override
    public DissectionProtocol loadStatus(long dissectionProtocolId) {
        return dissectionProtocolMapper.selectBasicData(dissectionProtocolId);
    }

    @Override
    public DissectionProtocol loadBasicData(long dissectionProtocolId) {
        validateExistence(dissectionProtocolId);
        DissectionProtocol dissectionProtocol = dissectionProtocolMapper.selectBasicData(dissectionProtocolId);
        List<DeathStoryEntry> deathStoryEntries = deathStoryEntryMapper.selectDeathStoryEntries(dissectionProtocolId);
        List<HospitalWardEntry> hospitalWardEntries = hospitalWardEntryMapper.selectProtocolHospitalWardEntries(dissectionProtocolId);
        dissectionProtocol.getBasicData().getDeathStory().setStoryEntries(deathStoryEntries);
        dissectionProtocol.getBasicData().getDeathStory().setHospitalWardEntries(hospitalWardEntries);
        return dissectionProtocol;
    }

    @Override
    public DissectionProtocol loadClinicalDiagnosis(long dissectionProtocolId) {
        validateExistence(dissectionProtocolId);
        return dissectionProtocolMapper.selectClinicalDiagnosis(dissectionProtocolId);
    }

    @Override
    public DissectionProtocol loadDescription(long dissectionProtocolId) {
        validateExistence(dissectionProtocolId);
        List<DescriptionPoint> descriptionPoints = descriptionPointMapper.selectDissectionProtocolDescriptionPoints(dissectionProtocolId);
        DissectionProtocol dissectionProtocol = new DissectionProtocol();
        dissectionProtocol.setId(dissectionProtocolId);
        dissectionProtocol.setDescriptionPointList(descriptionPoints);
        return dissectionProtocol;
    }

    @Override
    public DissectionProtocol loadDissectionDiagnosis(long dissectionProtocolId) {
        validateExistence(dissectionProtocolId);
        List<DissectionDiagnose> dissectionDiagnoses = dissectionDiagnoseMapper.selectDissectionDiagnoseForDissectionProtocol(dissectionProtocolId);
        DissectionProtocol dissectionProtocol = new DissectionProtocol();
        dissectionProtocol.setId(dissectionProtocolId);
        dissectionProtocol.setDissectionDiagnoseList(dissectionDiagnoses);
        return dissectionProtocol;
    }

    @Override
    @Transactional
    public void updateBasicData(DissectionProtocol dissectionProtocol) {
        validateExistence(dissectionProtocol);

        // Basic data is on all pages
        reportService.updateStatus(dissectionProtocol.getId(), ReportStatus.NEED_ALL);

        dissectionProtocolMapper.updateBasicData(dissectionProtocol);
        long dissectionProtocolId = dissectionProtocol.getId();
        deathStoryEntryMapper.deleteDeathStoryEntries(dissectionProtocolId);
        for (DeathStoryEntry deathStoryEntry : dissectionProtocol.getBasicData().getDeathStory().getStoryEntries()) {
            deathStoryEntry.setDissectionProtocolId(dissectionProtocolId);
            deathStoryEntryMapper.insertDeathStoryEntry(deathStoryEntry);
        }
        hospitalWardEntryMapper.deleteHospitalWardEntries(dissectionProtocolId);
        for (HospitalWardEntry hospitalWardEntry : dissectionProtocol.getBasicData().getDeathStory().getHospitalWardEntries()) {
            hospitalWardEntry.setDissectionProtocolId(dissectionProtocolId);
            hospitalWardEntryMapper.insertHospitalWardEntry(hospitalWardEntry);
        }
    }

    public List<DissectionProtocol> loadLatest(Range<Integer> range) {
        return dissectionProtocolMapper.selectLatest(range);
    }

    @Override
    public List<DissectionProtocol> search(String username) {
        List<UserSearch> userSearches = userSearchMapper.selectUserSearch(username);
        if (userSearches.isEmpty()) {
            return Collections.emptyList();
        } else {
            UserSearch userSearch = userSearches.get(0);
            return dissectionProtocolMapper.selectSearch(userSearch);
        }
    }

    @Override
    public List<DissectionProtocol> search(UserSearch userSearch) {
        return dissectionProtocolMapper.selectSearch(userSearch);
    }

    @Override
    @Transactional
    public void updateClinicalDiagnosis(DissectionProtocol dissectionProtocol) {
        reportService.updateStatus(dissectionProtocol.getId(), ReportStatus.NEED_FIRST_PAGE);
        dissectionProtocolMapper.updateClinicalDiagnosis(dissectionProtocol);
    }

    @Override
    public DescriptionPoint getDescriptionPoint(long descriptionPointId) {
        return descriptionPointMapper.selectDescriptionPoint(descriptionPointId);
    }

    @Override
    @Transactional
    public void updateDescriptionPoint(DescriptionPoint descriptionPoint) {
        reportService.updateStatus(descriptionPoint.getDissectionProtocolId(), ReportStatus.NEED_CONTENT_PAGES);
        descriptionPointMapper.updateDescriptionPoint(descriptionPoint);
    }

    @Override
    @Transactional
    public void updateDescriptionPoints(List<DescriptionPoint> descriptionPoints) {
        if (CollectionUtils.isEmpty(descriptionPoints)) {
            return;
        }
        reportService.updateStatus(descriptionPoints.get(0).getDissectionProtocolId(), ReportStatus.NEED_CONTENT_PAGES);
        for(DescriptionPoint descriptionPoint : descriptionPoints) {
            descriptionPointMapper.updateDescriptionPoint(descriptionPoint);
        }
    }

    @Override
    public DissectionDiagnose getDissectionDiagnose(long dissectionDiagnoseId) {
        return dissectionDiagnoseMapper.selectDissectionDiagnose(dissectionDiagnoseId);
    }

    @Override
    @Transactional
    public long createNewDissectionDiagnose(DissectionDiagnose dissectionDiagnose) {
        long dissectionProtocolId = dissectionDiagnose.getDissectionProtocolId();
        validateExistence(dissectionProtocolId);

        reportService.updateStatus(dissectionProtocolId, ReportStatus.NEED_ALL);

        // First create dissection diagnose source
        DissectionDiagnoseSource dissectionDiagnoseSource = new DissectionDiagnoseSource();
        dissectionDiagnoseSource.setName(dissectionDiagnose.getName());
        dissectionDiagnoseSourceService.create(dissectionDiagnoseSource);

        // Description point will not be created - since it has no position
//        DescriptionPointSource descriptionPointSource = descriptionPointSourceMapper.selectDescriptionPointSource(dissectionDiagnoseSource.getDescriptionPointSourceId());
//        DescriptionPoint descriptionPoint = new DescriptionPoint();
//        descriptionPoint.setDissectionProtocolId(dissectionProtocolId);
//        descriptionPoint.setDescriptionPointSource(descriptionPointSource);
//        descriptionPointMapper.insertDescriptionPoint(descriptionPoint);

        dissectionDiagnose.setDissectionDiagnoseSourceId(dissectionDiagnoseSource.getId());
        dissectionDiagnose.setDescriptionPointId(null);

        Integer maxSortIndex = dissectionDiagnoseMapper.selectMaxSortIndexForNewDissectionDiagnose(dissectionProtocolId);
        int newSortIndex = 1 + (maxSortIndex == null ? 0 : maxSortIndex);
        dissectionDiagnose.setSortIndex(newSortIndex);
        dissectionDiagnoseMapper.insertDissectionDiagnose(dissectionDiagnose);

        return dissectionDiagnoseSource.getDescriptionPointSourceId();
    }

    @Override
    @Transactional
    public void updateDissectionDiagnose(DissectionDiagnose dissectionDiagnose) {
        reportService.updateStatus(dissectionDiagnose.getDissectionProtocolId(), ReportStatus.NEED_ALL);
        DissectionDiagnose existingDissectionDiagnose = dissectionDiagnoseMapper.selectDissectionDiagnose(dissectionDiagnose.getId());
        existingDissectionDiagnose.setName(dissectionDiagnose.getName());
        dissectionDiagnoseMapper.updateDissectionDiagnose(existingDissectionDiagnose);
    }

    @Transactional
    @Override
    public void reorderDissectionDiagnose(long protocolId, List<String> ordered) {
        List<DissectionDiagnose> dissectionDiagnoses = dissectionDiagnoseMapper.selectDissectionDiagnoseForDissectionProtocol(protocolId);
        if (dissectionDiagnoses.size() == ordered.size()) {
            Map<Long, DissectionDiagnose> dissectionDiagnosesMap = Maps.uniqueIndex(dissectionDiagnoses, new com.google.common.base.Function<DissectionDiagnose, Long>() {
                @Override
                public Long apply(DissectionDiagnose dissectionDiagnose) {
                    return dissectionDiagnose.getId();
                }
            });
            for(int sortIndex = 0; sortIndex < ordered.size(); sortIndex++) {
                String dissectionDiagnoseId = ordered.get(sortIndex);
                DissectionDiagnose dissectionDiagnose = dissectionDiagnosesMap.get(Long.parseLong(dissectionDiagnoseId));
                if (dissectionDiagnose != null) {
                    dissectionDiagnose.setSortIndex(sortIndex);
                } else {
                    log.error("Dissection diagnose <{}> does not exists in protocol <{}>", dissectionDiagnoseId, protocolId);
                    return;
                }
            }
            for(DissectionDiagnose dissectionDiagnose : dissectionDiagnosesMap.values()) {
                dissectionDiagnoseMapper.updateDissectionDiagnoseSortIndex(dissectionDiagnose);
            }
        }
    }

    @Override
    @Deprecated
    public void reorderDissectionDiagnose(long dissectionProtocolId, long dissectionDiagnoseId, OrderSwitch orderSwitch) {
        List<DissectionDiagnose> dissectionDiagnoses = dissectionDiagnoseMapper.selectDissectionDiagnoseForDissectionProtocol(dissectionProtocolId);
        if (dissectionDiagnoses == null || dissectionDiagnoses.size() < 1) {
            log.warn("Reorder of dissection diagnose aborted since collection does not have at leas two elements");
            return;
        }
        Collections.sort(dissectionDiagnoses, new Comparator<DissectionDiagnose>() {
            @Override
            public int compare(DissectionDiagnose o1, DissectionDiagnose o2) {
                Integer s1 = o1.getSortIndex();
                Integer s2 = o2.getSortIndex();
                return s1.compareTo(s2);
            }
        });
        List<DissectionDiagnose> before = new LinkedList<DissectionDiagnose>();
        List<DissectionDiagnose> after = new LinkedList<DissectionDiagnose>();
        DissectionDiagnose selected = null;
        for (DissectionDiagnose dissectionDiagnose : dissectionDiagnoses) {
            if (dissectionDiagnose.getId() == dissectionDiagnoseId) {
                selected = dissectionDiagnose;
            } else {
                if (selected == null) {
                    before.add(dissectionDiagnose);
                } else {
                    after.add(dissectionDiagnose);
                }
            }
        }

        if (selected == null) {
            log.warn("Reorder of dissection diagnose aborted since selected dissection diagnose was not found");
            return;
        }

        switch (orderSwitch) {
            case STEP_UP:
                if (before.isEmpty()) {
                    log.warn("Reorder of dissection diagnose aborted since target position is out of bound: possible the first element");
                    return;
                } else {
                    DissectionDiagnose lastElementBefore = before.remove(before.size() - 1);
                    before.add(selected);
                    before.add(lastElementBefore);
                }
                break;
            case STEP_DOWN:
                if (after.isEmpty()) {
                    log.warn("Reorder of dissection diagnose aborted since target position is out of bound: possible the last element");
                    return;
                } else {
                    after.add(1, selected);
                }
                break;
            case FULL_DOWN:
                after.add(selected);
                break;
            case FULL_UP:
                before.add(0, selected);
                break;
        }

        List<DissectionDiagnose> newOrderedDissectionDiagnoses = new ArrayList<DissectionDiagnose>();
        newOrderedDissectionDiagnoses.addAll(before);
        newOrderedDissectionDiagnoses.addAll(after);
        int sortIndex = 1;
        for (DissectionDiagnose dissectionDiagnose : newOrderedDissectionDiagnoses) {
            int currentSortIndex = dissectionDiagnose.getSortIndex();
            if (currentSortIndex != sortIndex) {
                dissectionDiagnose.setSortIndex(sortIndex);
                dissectionDiagnoseMapper.updateDissectionDiagnoseSortIndex(dissectionDiagnose);
            }
            sortIndex++;
        }
        if (sortIndex > 1) {
            reportService.updateStatus(dissectionProtocolId, ReportStatus.NEED_ALL);
        }
    }

    @Override
    @Transactional
    public DescriptionPointSource addDissectionDiagnose(DissectionDiagnose dissectionDiagnose) {
        long dissectionProtocolId = dissectionDiagnose.getDissectionProtocolId();
        validateExistence(dissectionProtocolId);

        reportService.updateStatus(dissectionProtocolId, ReportStatus.NEED_ALL);
        long dissectionDiagnoseSourceId = dissectionDiagnose.getDissectionDiagnoseSourceId();
        DissectionDiagnoseSource dissectionDiagnoseSource = dissectionDiagnoseSourceService.read(dissectionDiagnoseSourceId);
        long descriptionPointSourceId = dissectionDiagnoseSource.getDescriptionPointSourceId();
        DescriptionPointSource descriptionPointSource = descriptionPointSourceMapper.selectDescriptionPointSource(descriptionPointSourceId);
        if (descriptionPointSource.getPoint() > 0) {
            DescriptionPoint descriptionPoint = new DescriptionPoint();
            descriptionPoint.setDissectionProtocolId(dissectionProtocolId);
            descriptionPoint.setDescriptionPointSource(descriptionPointSource);
            descriptionPointMapper.insertDescriptionPoint(descriptionPoint);
            dissectionDiagnose.setDescriptionPointId(descriptionPoint.getId());
        }

        dissectionDiagnose.setDissectionDiagnoseSourceId(dissectionDiagnoseSource.getId());
        Integer maxSortIndex = dissectionDiagnoseMapper.selectMaxSortIndexForNewDissectionDiagnose(dissectionProtocolId);
        int newSortIndex = 1 + (maxSortIndex == null ? 0 : 1);
        dissectionDiagnose.setSortIndex(newSortIndex);
        dissectionDiagnoseMapper.insertDissectionDiagnose(dissectionDiagnose);

        return descriptionPointSource;
    }

    @Override
    public DissectionProtocolProgress getProgress(long dissectionProtocolId) {
        return dissectionProtocolMapper.selectProgress(dissectionProtocolId);
    }

    @Override
    public DissectionDiagnose loadClinicalDiagnoseForDescriptionPoint(long dissectionProtocolId, long descriptionPointId) {
        Map<String, Long> selectParams = new HashMap<String, Long>();
        selectParams.put("dissectionProtocolId", dissectionProtocolId);
        selectParams.put("descriptionPointId", descriptionPointId);
        List<DissectionDiagnose> dissectionDiagnoses = dissectionDiagnoseMapper.selectDissectionDiagnoseFromDescriptionPoint(selectParams);
        if (CollectionUtils.isEmpty(dissectionDiagnoses)) {
            return null;
        } else {
            return dissectionDiagnoses.get(0);
        }
    }

    @Override
    @Transactional
    public DescriptionPoint deleteDescriptionPoint(long descriptionPointId) {
        DescriptionPoint descriptionPoint = descriptionPointMapper.selectDescriptionPoint(descriptionPointId);
        if (descriptionPoint != null) {
            dissectionDiagnoseMapper.updateDeletedDescriptionPoint(descriptionPointId);
            descriptionPointMapper.deleteDescriptionPoint(descriptionPointId);
            reportService.updateStatus(descriptionPoint.getDissectionProtocolId(), ReportStatus.NEED_ALL);
            return descriptionPoint;
        } else {
            return null;
        }
    }

    @Override
    public void removeWard(long dissectionProtocolId, long hospitalWardId) {
        Map<String, Object> removeParams = new HashMap<String, Object>();
        removeParams.put("dissectionProtocolId", dissectionProtocolId);
        removeParams.put("hospitalWardId", hospitalWardId);
        hospitalWardEntryMapper.deleteHospitalWardFromDissectionProtocol(removeParams);
    }

    @Override
    @Transactional
    public void delete(long dissectionProtocolId) {
        dissectionDiagnoseMapper.deleteDissectionProtocolDissectionDiagnose(dissectionProtocolId);
        descriptionPointMapper.deleteDissectionProtocolDescriptionPoints(dissectionProtocolId);
        deathStoryEntryMapper.deleteDeathStoryEntries(dissectionProtocolId);
        hospitalWardEntryMapper.deleteHospitalWardEntries(dissectionProtocolId);
        histopathologicalExaminationMapper.deleteDissectionProtocolHistopathologicalExamination(dissectionProtocolId);
        reportMapper.deleteDissectionProtocolReport(dissectionProtocolId);
        dissectionProtocolMapper.deleteDissectionProtocol(dissectionProtocolId);
    }

    @Override
    public void deleteHistopathologicalExamination(long histopathologicalExaminationId) {
        histopathologicalExaminationMapper.deleteHistopathologicalExamination(histopathologicalExaminationId);
    }

    @Override
    public long clone(long dissectionProtocolId) {
        validateExistence(dissectionProtocolId);

        dissectionProtocolMapper.insertClone(dissectionProtocolId);
        long newDissectionProtocolId = dissectionProtocolMapper.selectNewest();

        List<DissectionDiagnose> dissectionDiagnoses = dissectionDiagnoseMapper.selectDissectionDiagnoseForDissectionProtocol(dissectionProtocolId);
        List<DescriptionPoint> descriptionPoints = descriptionPointMapper.selectDissectionProtocolDescriptionPoints(dissectionProtocolId);
        Map<Long, DescriptionPoint> descriptionPointMap = collectionSupport.convert(descriptionPoints);

        for (DissectionDiagnose dissectionDiagnose : dissectionDiagnoses) {
            dissectionDiagnose.setId(0);
            dissectionDiagnose.setDissectionProtocolId(newDissectionProtocolId);
            Long descriptionPointId = dissectionDiagnose.getDescriptionPointId();
            if (descriptionPointId != null) {
                DescriptionPoint descriptionPoint = descriptionPointMap.remove(descriptionPointId);
                if (descriptionPoint != null) {
                    descriptionPoint.setId(0);
                    descriptionPoint.setDissectionProtocolId(newDissectionProtocolId);
                    descriptionPointMapper.insertCloneDescriptionPoint(descriptionPoint);
                    dissectionDiagnose.setDescriptionPointId(descriptionPoint.getId());
                }
            }
            dissectionDiagnoseMapper.insertDissectionDiagnose(dissectionDiagnose);
        }
        for (DescriptionPoint descriptionPoint : descriptionPointMap.values()) {
            descriptionPoint.setId(0);
            descriptionPoint.setDissectionProtocolId(newDissectionProtocolId);
            descriptionPointMapper.insertCloneDescriptionPoint(descriptionPoint);
        }

        Map<String, Long> protocolIds = new HashMap<String, Long>();
        protocolIds.put("sourceDissectionProtocolId", dissectionProtocolId);
        protocolIds.put("targetDissectionProtocolId", newDissectionProtocolId);
        histopathologicalExaminationMapper.insertClone(protocolIds);
        reportMapper.insertEmptyReport(newDissectionProtocolId);

        return newDissectionProtocolId;
    }

    @Override
    @Transactional
    public void deleteDissectionDiagnose(long dissectionDiagnoseId) {
        DissectionDiagnose dissectionDiagnose = dissectionDiagnoseMapper.selectDissectionDiagnose(dissectionDiagnoseId);
        if (dissectionDiagnose != null) {
            dissectionDiagnoseOptionMapper.deleteDissectionDiagnoseOptionByDissectionDiagnose(dissectionDiagnoseId);
            dissectionDiagnoseMapper.deleteDissectionDiagnose(dissectionDiagnoseId);
            long dissectionProtocolId = dissectionDiagnose.getDissectionProtocolId();
            reportService.updateStatus(dissectionProtocolId, ReportStatus.NEED_ALL);
            Long descriptionPointId = dissectionDiagnose.getDescriptionPointId();
            if (descriptionPointId != null) {
                DescriptionPoint descriptionPoint = descriptionPointMapper.selectDescriptionPoint(descriptionPointId);
                if (descriptionPoint != null) {
                    descriptionPointMapper.deleteDescriptionPoint(descriptionPointId);

                    // We need to bring back default description point.
                    DissectionProtocol dissectionProtocol = dissectionProtocolMapper.selectBasicData(dissectionProtocolId);
                    DissectionProtocolCategory.Name category = dissectionProtocol.getCategory();
                    Map<String, Object> parameters = new HashMap<String, Object>();
                    parameters.put("descriptionPointType", DescriptionPointType.GENERAL.name());
                    parameters.put("point", descriptionPoint.getDescriptionPointSource().getPoint());
                    parameters.put("position", descriptionPoint.getDescriptionPointSource().getPosition());
                    parameters.put("categoryName", category.name());
                    List<DescriptionPointSource> descriptionPointSources = descriptionPointSourceMapper.selectSpecificDescriptionPoint(parameters);
                    if (!descriptionPointSources.isEmpty()) {
                        DescriptionPointSource descriptionPointSource = descriptionPointSources.get(0);
                        descriptionPoint.setId(0);
                        descriptionPoint.setDescriptionPointSource(descriptionPointSource);
                        descriptionPointMapper.insertDescriptionPoint(descriptionPoint);
                    }
                }
            }
        }
    }

    @Override
    public DissectionDiagnose loadSingleDissectionDiagnose(long dissectionDiagnoseId) {
        return dissectionDiagnoseMapper.selectDissectionDiagnose(dissectionDiagnoseId);
    }

    @Override
    public Multimap<Long, DissectionDiagnoseOption> getDissectionDiagnoseOptions(Set<Long> dissectionDiagnoseIds) {
        if (CollectionUtils.isEmpty(dissectionDiagnoseIds)) {
            return HashMultimap.create();
        }
        List<Long> dissectionDiagnoseIdList = new ArrayList<Long>(dissectionDiagnoseIds);
        List<DissectionDiagnoseOption> dissectionDiagnoseOptions = dissectionDiagnoseOptionMapper.selectOptionsForDissectionDiagnoses(dissectionDiagnoseIdList);
        Multimap<Long, DissectionDiagnoseOption> optionMultimap = LinkedHashMultimap.create();
        for (DissectionDiagnoseOption dissectionDiagnoseOption : dissectionDiagnoseOptions) {
            optionMultimap.put(dissectionDiagnoseOption.getDissectionDiagnoseId(), dissectionDiagnoseOption);
        }
        return optionMultimap;
    }

    @Override
    public List<DissectionDiagnoseOption> getDissectionDiagnoseOptions(long dissectionDiagnoseId) {
        return dissectionDiagnoseOptionMapper.selectOptionsForDissectionDiagnose(dissectionDiagnoseId);
    }

    @Override
    @Transactional
    public void addDissectionDiagnoseOption(long dissectionDiagnoseId, long dissectionDiagnoseSourceOptionId) {
        DissectionDiagnose dissectionDiagnose = dissectionDiagnoseMapper.selectDissectionDiagnose(dissectionDiagnoseId);
        if (dissectionDiagnose != null) {
            DissectionDiagnoseSourceOption dissectionDiagnoseSourceOption = dissectionDiagnoseSourceOptionMapper.selectOption(dissectionDiagnoseSourceOptionId);
            DissectionDiagnoseOption dissectionDiagnoseOption = new DissectionDiagnoseOption();
            dissectionDiagnoseOption.setName(dissectionDiagnoseSourceOption.getName());
            dissectionDiagnoseOption.setDissectionDiagnoseId(dissectionDiagnoseId);
            dissectionDiagnoseOption.setDissectionDiagnoseSourceOptionId(dissectionDiagnoseSourceOptionId);
            dissectionDiagnoseOption.setSortIndex(dissectionDiagnoseSourceOption.getSortIndex());
            dissectionDiagnoseOptionMapper.insertDissectionDiagnoseOption(dissectionDiagnoseOption);
            reportService.updateStatus(dissectionDiagnose.getDissectionProtocolId(), ReportStatus.NEED_FIRST_PAGE);
        }
    }

    @Override
    public void deleteDissectionDiagnoseOption(long dissectionDiagnoseOptionId) {
        dissectionDiagnoseOptionMapper.deleteDissectionDiagnoseOption(dissectionDiagnoseOptionId);
    }

    @Override
    public void updateDissectionDiagnoseOption(DissectionDiagnoseOption dissectionDiagnoseOption) {
        dissectionDiagnoseOptionMapper.updateDissectionDiagnoseOption(dissectionDiagnoseOption);
    }

    @Override
    public List<DissectionProtocol> getDissectionDiagnoseProtocols(long dissectionDiagnoseSourceId) {
        List<Long> dissectionProtocolIds = dissectionDiagnoseMapper.selectDissectionProtocolIdsForSource(dissectionDiagnoseSourceId);
        if (dissectionProtocolIds.isEmpty()) {
            return Collections.emptyList();
        } else {
            return dissectionProtocolMapper.selectDissectionProtocolsBasicData(dissectionProtocolIds);
        }
    }

    @Override
    public DissectionProtocol loadHistopathologicalExamination(long dissectionProtocolId) {
        List<HistopathologicalExamination> histopathologicalExaminations = histopathologicalExaminationMapper.selectHistopathologicalExaminationForDissectionProtocol(dissectionProtocolId);
        DissectionProtocol dissectionProtocol = new DissectionProtocol();
        dissectionProtocol.setId(dissectionProtocolId);
        dissectionProtocol.setHistopathologicalExaminations(histopathologicalExaminations);
        return dissectionProtocol;
    }

    @Override
    public DissectionProtocol loadClinicalData(long dissectionProtocolId) {
        return dissectionProtocolMapper.selectClinicalData(dissectionProtocolId);
    }

    @Override
    public DissectionProtocol loadMedicalPracticeAnalysis(long dissectionProtocolId) {
        return dissectionProtocolMapper.selectMedicalPracticeAnalysis(dissectionProtocolId);
    }

    @Override
    @Transactional
    public void updateHistopatologicalExamination(HistopathologicalExamination histopathologicalExamination) {
        reportService.updateStatus(histopathologicalExamination.getDissectionProtocolId(), ReportStatus.NEED_CONTENT_PAGES);
        validateExistence(histopathologicalExamination.getDissectionProtocolId());
        if (histopathologicalExamination.getId() > 0) {
            histopathologicalExaminationMapper.updateHistopathologicalExamination(histopathologicalExamination);
        } else {
            histopathologicalExaminationMapper.insertHistopathologicalExamination(histopathologicalExamination);
        }
    }

    @Override
    @Transactional
    public void updateMedicalPracticeAnalysis(DissectionProtocol dissectionProtocol) {
        validateExistence(dissectionProtocol);
        reportService.updateStatus(dissectionProtocol.getId(), ReportStatus.NEED_CONTENT_PAGES);
        dissectionProtocolMapper.updateMedicalPracticeAnalysis(dissectionProtocol);
    }

    @Override
    @Transactional
    public void updateClinicalData(DissectionProtocol dissectionProtocol) {
        validateExistence(dissectionProtocol);
        reportService.updateStatus(dissectionProtocol.getId(), ReportStatus.NEED_CONTENT_PAGES);
        dissectionProtocolMapper.updateClinicalData(dissectionProtocol);
    }

    @Override
    public void updateStatus(long dissectionProtocolId, DissectionProtocolStatus dissectionProtocolStatus) {
        DissectionProtocol dissectionProtocol = dissectionProtocolMapper.selectStatus(dissectionProtocolId);
        if (dissectionProtocol != null) {
            dissectionProtocol.setStatus(dissectionProtocolStatus);
            dissectionProtocolMapper.updateStatus(dissectionProtocol);
        }
    }

    /**
     * This methods creates default description points for protocol based on category.
     *
     * @param category taken as template for description points
     * @param dissectionProtocolId id of description protocol.
     */
    private void createDefaultDescriptionPoints(DissectionProtocolCategory.Name category, long dissectionProtocolId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("category", category.name());
        map.put("dissectionProtocolId", dissectionProtocolId);
        descriptionPointMapper.insertDescriptionPointsForNewProtocol(map);
    }

    private void validateExistence(DissectionProtocol dissectionProtocol) {
        if (dissectionProtocol == null) {
            throw new ProtocolNotExistsException(0);
        }
        validateExistence(dissectionProtocol.getId());
    }

    private void validateExistence(long dissectionProtocolId) {
        Long id = dissectionProtocolMapper.selectId(dissectionProtocolId);
        if (id == null) {
            throw new ProtocolNotExistsException(dissectionProtocolId);
        } else if (!id.equals(dissectionProtocolId)) {
            throw new IllegalStateException(String.format("Returned protocol id <%s> does not match requested id <%s>", String.valueOf(id), String.valueOf(dissectionProtocolId)));
        }
    }
}
