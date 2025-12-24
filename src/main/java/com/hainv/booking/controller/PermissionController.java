package com.hainv.booking.controller;

import com.hainv.booking.entity.dto.FeatureDTO;
import com.hainv.booking.entity.user.Feature;
import com.hainv.booking.service.IPermissionService;
import com.hainv.booking.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final IPermissionService permissionService;

    @GetMapping("/me")
    public ApiResponse<List<Feature>> getRolePermission(Authentication authentication) {
        String accountId = authentication.getName(); // hoặc từ JWT
        return ApiResponse.success(permissionService.getRole(accountId));
    }

    @GetMapping("/menus")
    public ApiResponse<List<FeatureDTO>> getMenus(Authentication authentication) {
        String accountId = authentication.getName(); // hoặc từ JWT
        return ApiResponse.success(permissionService.getUserMenus(accountId));
    }
}
