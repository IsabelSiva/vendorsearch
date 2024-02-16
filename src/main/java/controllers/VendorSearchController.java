package controllers;

import entities.Job;
import entities.Vendor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VendorSearchController {
    private static List<Vendor> vendors = new ArrayList<>();
    private static List<Job> jobs = new ArrayList<>();

    static {
        vendors.add(new Vendor(1L, "Vendor1", "Region1", "ServiceCategory1", true));
        vendors.add(new Vendor(2L, "Vendor2", "Region2", "ServiceCategory2", false));

        jobs.add(new Job(1L, "ServiceCategory1", "Region1"));
        jobs.add(new Job(2L, "ServiceCategory2", "Region2"));
    }

    @GetMapping("/potentialVendors")
    public List<Vendor> getPotentialVendors(@RequestParam Long jobId) {
        Job job = findJobById(jobId);
        if (job != null) {
            return filterPotentialVendors(job);
        }
        return new ArrayList<>();
    }

    @PostMapping("/createVendor")
    public void createVendor(@RequestBody Vendor vendor) {
        vendors.add(vendor);
    }

    @PostMapping("/createJob")
    public void createJob(@RequestBody Job job) {
        jobs.add(job);
    }

    @GetMapping("/public/vendorStats")
    public Map<String, Object> getVendorStats(@RequestParam Long jobId) {
        Job job = findJobById(jobId);
        if (job != null) {
            int totalVendors = vendors.size();
            int compliantVendors = (int) vendors.stream().filter(Vendor::isCompliant).count();
            int nonCompliantVendors = totalVendors - compliantVendors;

            Map<String, Object> stats = new HashMap<>();
            stats.put("totalVendors", totalVendors);
            stats.put("compliantVendors", compliantVendors);
            stats.put("nonCompliantVendors", nonCompliantVendors);

            return stats;
        }
        return new HashMap<>();
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
