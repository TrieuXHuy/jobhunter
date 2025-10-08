package vn.huy.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.huy.jobhunter.domain.Permission;
import vn.huy.jobhunter.domain.response.ResultPaginationDTO;
import vn.huy.jobhunter.service.PermissionService;
import vn.huy.jobhunter.util.annotition.ApiMessage;
import vn.huy.jobhunter.util.error.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/permissions")
    @ApiMessage("create a permission")
    public ResponseEntity<Permission> createNewPermission(@Valid @RequestBody Permission reqPermission)
            throws ResourceNotFoundException {
        Boolean isExists = this.permissionService.isApiExisting(reqPermission.getApiPath(),
                reqPermission.getMethod(),
                reqPermission.getModule());

        if (isExists) {
            throw new ResourceNotFoundException(
                    "Permission đã tồn tại");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.permissionService.handleCreatePermission(reqPermission));
    }

    @PutMapping("/permissions")
    @ApiMessage("update a permission")
    public ResponseEntity<Permission> updatePermission(@Valid @RequestBody Permission reqPermission)
            throws ResourceNotFoundException {
        Boolean isExists = this.permissionService.isApiExisting(reqPermission.getApiPath(),
                reqPermission.getMethod(),
                reqPermission.getModule());

        if (isExists) {
            throw new ResourceNotFoundException(
                    "Permission đã tồn tại");
        }

        if (!this.permissionService.isIdExisting(reqPermission.getId())) {
            throw new ResourceNotFoundException(
                    "Id không tồn tại");
        }

        return ResponseEntity.ok(this.permissionService.handleUpdatePermission(reqPermission));
    }

    @GetMapping("/permissions")
    @ApiMessage("get all permission")
    public ResponseEntity<ResultPaginationDTO> getAllPermissions(@Filter Specification<Permission> spec,
            Pageable pageable) {
        return ResponseEntity.ok(this.permissionService.fetchAllPermissions(spec, pageable));
    }

    @DeleteMapping("/permissions/{id}")
    @ApiMessage("delete a permission")
    public ResponseEntity<Void> DeletePermission(@PathVariable("id") long id) throws ResourceNotFoundException {
        if (!this.permissionService.isIdExisting(id)) {
            throw new ResourceNotFoundException(
                    "Id không tồn tại");
        }
        this.permissionService.deletePermission(id);
        return ResponseEntity.ok(null);
    }
}
