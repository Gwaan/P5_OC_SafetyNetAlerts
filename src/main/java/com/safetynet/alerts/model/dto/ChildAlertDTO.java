package com.safetynet.alerts.model.dto;

import lombok.Data;

import java.util.List;

/**
 * Child alert dto.
 *
 * @author Gwen
 * @version 1.0
 */
@Data
public class ChildAlertDTO {

    /**
     * The Adults.
     */
    List<PersonsCoveredByStationDTO> adults;

    /**
     * The Children.
     */
    List<PersonsCoveredByStationDTO> children;

}
