package com.ra.dissection.protocol.dao.protocol;

import com.ra.dissection.protocol.domain.protocol.DeathStoryEntry;

import java.util.List;

/**
 * @author lukaszkaleta
 * @since 03.05.13 06:17
 */
public interface DeathStoryEntryMapper {

    void insertDeathStoryEntry(DeathStoryEntry deathStoryEntry);

    void updateDeathStoryEntry(DeathStoryEntry deathStoryEntry);

    void deleteDeathStoryEntry(long id);

    void deleteDeathStoryEntries(long dissectionProtocolId);

    DeathStoryEntry selectDeathStoryEntry(long id);

    List<DeathStoryEntry> selectDeathStoryEntries(long dissectionProtocolId);
}
