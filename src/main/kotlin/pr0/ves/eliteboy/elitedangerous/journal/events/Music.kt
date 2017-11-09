package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class Music : JournalEntry() {
    var MusicTrack: String? = null
    fun musicTrackID() = EDMusicTrackEnum.valueOf(MusicTrack!!)

    enum class EDMusicTrackEnum {
        None,
        Unknown,
        NoTrack,
        MainMenu,
        CQCMenu,
        SystemMap,
        GalaxyMap,
        GalacticPowers,
        CQC,
        DestinationFromHyperspace,
        DestinationFromSupercruise,
        Supercruise,
        Combat_Unknown,
        UnknownEncounter,
        CapitalShip,
        CombatLargeDogFight,
        CombatDogfight,
        CombatSRV,
        UnknownSettlement,
        DockingComputer,
        Starport,
        UnknownExploration,
        Exploration
    }
}