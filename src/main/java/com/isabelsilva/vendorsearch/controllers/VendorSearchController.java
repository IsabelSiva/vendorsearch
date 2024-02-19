package com.isabelsilva.vendorsearch.controllers;

import com.isabelsilva.vendorsearch.entities.Job;
import com.isabelsilva.vendorsearch.entities.Vendor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class VendorSearchController {
    private static List<Vendor> vendors = new ArrayList<>();
    private static List<Job> jobs = new ArrayList<>();

    @PostMapping("/createJob")
    public ResponseEntity<String> createJob(@RequestBody Job job) {
        jobs.add(job);
        return ResponseEntity.status(HttpStatus.CREATED).body("Job created");
    }

    @PostMapping("/createVendor")
    public ResponseEntity<String> createVendor(@RequestBody Vendor vendor) {
        vendors.add(vendor);
        return ResponseEntity.status(HttpStatus.CREATED).body("Vendor created");
    }

    @GetMapping("/potentialVendors")
    public ResponseEntity<List<Vendor>> getPotentialVendors(@RequestParam Long jobId) {
        Job job = findJobById(jobId);
        if (job != null) {
            return ResponseEntity.ok(filterPotentialVendors(job));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/vendors")
    public ResponseEntity<List<Vendor>> getVendors() {
        return ResponseEntity.ok(vendors);
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getJobs() {
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/amountVendors")
    public ResponseEntity<Map<String, Object>> getAmountVendors(@RequestParam Long vendorId) {
        Vendor vendor = findVendorById(vendorId);

        List<Vendor> vendorsWithSameLocation = vendors.stream()
                .filter(v -> v.getLocation().equals(vendor.getLocation()))
                .toList();

        if (!vendorsWithSameLocation.isEmpty()) {
            int totalVendors = vendorsWithSameLocation.size();
            long compliantVendors = vendorsWithSameLocation.stream().filter(Vendor::isCompliant).count();
            int nonCompliantVendors = totalVendors - (int) compliantVendors;

            Map<String, Object> stats = new HashMap<>();
            stats.put("totalVendors", totalVendors);
            stats.put("compliantVendors", compliantVendors);
            stats.put("nonCompliantVendors", nonCompliantVendors);

            return ResponseEntity.ok(stats);
        }

        return ResponseEntity.notFound().build();
    }


    private Vendor findVendorById(Long vendorId) {
        return vendors.stream().filter(job -> job.getId().equals(vendorId)).findFirst().orElse(null);
    }

    private List<Vendor> filterPotentialVendors(Job job) {
        return vendors.stream()
                .filter(vendor ->
                        vendor.getLocation().getName().equalsIgnoreCase(job.getLocation().getName()) &&
                                vendor.getServiceCategories() != null &&
                                vendor.getServiceCategories().stream()
                                        .anyMatch(serviceCategory ->
                                                serviceCategory.getName().equalsIgnoreCase(job.getServiceCategory().getName())
                                        )
                )
                .sorted(Comparator.comparing(Vendor::isCompliant).reversed())
                .collect(Collectors.toList());
    }

    private Job findJobById(Long jobId) {
        return jobs.stream().filter(job -> job.getId().equals(jobId)).findFirst().orElse(null);
    }
}
