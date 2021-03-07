package com.safetynet.alerts.model.dto;

import lombok.Data;

import java.util.List;

/**
 * Count and persons covered dto.
 *
 * @author Gwen
 * @version 1.0
 */
@Data
public class CountAndPersonsCoveredDTO {

    /**
     * The Count of adults.
     */
    int countOfAdults;
    /**
     * The Count of children.
     */
    int countOfChildren;

    /**
     * The Persons covered.
     */
    List<PersonsCoveredByStationDTO> personsCovered;

}
