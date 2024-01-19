package com.example.projet_ecommerce.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/add")
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
        Admin addedAdmin = adminService.addAdmin(admin);
        return new ResponseEntity<>(addedAdmin, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Admin>> listAdmins() {
        List<Admin> adminList = adminService.getAllAdmins();
        return new ResponseEntity<>(adminList, HttpStatus.OK);
    }
}
