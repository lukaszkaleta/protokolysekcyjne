package com.ra.dissection.protocol.domain.protocol;

import com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory;

import java.util.*;

import static com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory.Name.ADULT;
import static com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory.Name.NEWBORN;
import static com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory.Name.FETUS;

/**
 * @author lukaszkaleta
 * @since 01.05.13 08:58
 */
public enum DeathStoryName {

    ADMISSION(EnumSet.of(ADULT)),

    MOTHER_ADMISSION(EnumSet.of(NEWBORN, FETUS)),

    CHILD_BIRTH(EnumSet.of(NEWBORN)),

    DEATH_CHILD_BIRTH(EnumSet.of(FETUS)),

    DEATH(EnumSet.of(ADULT, NEWBORN));

    private final Set<DissectionProtocolCategory.Name> availableInCategories;

    private DeathStoryName(Set<DissectionProtocolCategory.Name> availableInCategories) {
        this.availableInCategories = availableInCategories;
    }

    public static Map<DissectionProtocolCategory.Name, List<DeathStoryName>> categoryMap() {
        Map<DissectionProtocolCategory.Name, List<DeathStoryName>> categoryMap = new HashMap<DissectionProtocolCategory.Name, List<DeathStoryName>>();
        categoryMap.put(null, Collections.<DeathStoryName>emptyList());
        for (DissectionProtocolCategory.Name dissectionProtocolCategory : DissectionProtocolCategory.Name.values()) {
            List<DeathStoryName> deathStoryNames = new ArrayList<DeathStoryName>();
            for (DeathStoryName deathStoryName : DeathStoryName.values()) {
                if (deathStoryName.availableInCategories.contains(dissectionProtocolCategory)) {
                    deathStoryNames.add(deathStoryName);
                }
            }
            categoryMap.put(dissectionProtocolCategory, deathStoryNames);
        }
        return categoryMap;
    }

    public static Map<DeathStoryName, Set<DissectionProtocolCategory.Name>> nameMap() {
        Map<DeathStoryName, Set<DissectionProtocolCategory.Name>> nameMap = new HashMap<DeathStoryName, Set<DissectionProtocolCategory.Name>>();
        for (DeathStoryName deathStoryName : values()) {
            nameMap.put(deathStoryName, deathStoryName.availableInCategories);
        }
        return nameMap;
    }

    public static Map<DeathStoryName, Map<DissectionProtocolCategory.Name, Boolean>> activeNameMap() {
        Map<DeathStoryName, Map<DissectionProtocolCategory.Name, Boolean>> nameMap = new HashMap<DeathStoryName, Map<DissectionProtocolCategory.Name, Boolean>>();
        for (DeathStoryName deathStoryName : values()) {
            Map<DissectionProtocolCategory.Name, Boolean> activeCategoryMap = new HashMap<DissectionProtocolCategory.Name, Boolean>();
            for (DissectionProtocolCategory.Name dissectionProtocolCategory : DissectionProtocolCategory.Name.values()) {
                activeCategoryMap.put(dissectionProtocolCategory, deathStoryName.availableInCategories.contains(dissectionProtocolCategory));
            }
            nameMap.put(deathStoryName, activeCategoryMap);
        }
        return nameMap;
    }
}
