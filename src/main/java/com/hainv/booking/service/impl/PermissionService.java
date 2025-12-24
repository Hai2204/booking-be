package com.hainv.booking.service.impl;

import com.hainv.booking.entity.dto.FeatureDTO;
import com.hainv.booking.entity.user.Feature;
import com.hainv.booking.repository.user.FeatureRepository;
import com.hainv.booking.service.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {
    private final FeatureRepository featureRepository;

    @Transactional(readOnly = true)
    @Override
    public List<FeatureDTO> getUserMenus(String accountId) {

        return featureRepository.findFeaturesByAccountId(accountId)
                .stream()
                .map(f -> new FeatureDTO(
                        f.getCodeId(),
                        f.getName(),
                        f.getUrl(),
                        f.getIcon()
                ))
                .toList();
    }

    @Override
    public List<Feature> getRole(String accountId) {
        return featureRepository.findFeaturesByAccountId(accountId);
    }
}
