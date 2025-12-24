package com.hainv.booking.service;

import com.hainv.booking.entity.dto.FeatureDTO;
import com.hainv.booking.entity.user.Feature;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IPermissionService {
    @Transactional(readOnly = true)
    List<FeatureDTO> getUserMenus(String accountId);

    List<Feature> getRole(String accountId);
}
