package com.isabelsilva.vendorsearch.controllers;

import com.isabelsilva.vendorsearch.entities.Job;
import com.isabelsilva.vendorsearch.entities.Vendor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Log4j2
public class VendorSearchController {
    private List<Vendor> vendors = new ArrayList<>();
    private List<Job> jobs = new ArrayList<>();

    @PostMapping("/createJob")
    public ResponseEntity<String> createJob(@RequestBody Job job) {
        jobs.add(job);
        return ResponseEntity.status(HttpStatus.CREATED).body("Job created");
    }

    @PostMapping("/createVendor")
    public ResponseEntity<String> createVendor(@RequestBody Vendor vendor) {
        log.error("uata réu meu cumpadi?");
        vendors.add(vendor);
        return ResponseEntity.status(HttpStatus.CREATED).body("Vendor created");
    }

    @GetMapping("/potentialVendors")
    public ResponseEntity<List<Vendor>> getPotentialVendors(@RequestParam Long jobId) {
        Job job = findJobById(jobId);
        if (job != null) {
            List<Vendor> potentialVendors = filterPotentialVendors(job);
            return ResponseEntity.ok(potentialVendors);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/public/vendorStats")
    public ResponseEntity<Map<String, Object>> getVendorStats(@RequestParam Long jobId) {
        Job job = findJobById(jobId);
        if (job != null) {
            int totalVendors = vendors.size();
            int compliantVendors = (int) vendors.stream().filter(Vendor::isCompliant).count();
            int nonCompliantVendors = totalVendors - compliantVendors;

            Map<String, Object> stats = new HashMap<>();
            stats.put("totalVendors", totalVendors);
            stats.put("compliantVendors", compliantVendors);
            stats.put("nonCompliantVendors", nonCompliantVendors);

            return ResponseEntity.ok(stats);
        }
        return ResponseEntity.notFound().build();
    }

    private List<Vendor> filterPotentialVendors(Job job) {
        return vendors.stream()
                .filter(vendor -> vendor.getLocation().equals(job.getLocation()) && vendor.getServiceCategory().equals(job.getServiceCategory()))
                .sorted((v1, v2) -> Boolean.compare(v2.isCompliant(), v1.isCompliant()))
                .collect(Collectors.toList());
    }

    private Job findJobById(Long jobId) {
        return jobs.stream().filter(job -> job.getId().equals(jobId)).findFirst().orElse(null);
    }
}
