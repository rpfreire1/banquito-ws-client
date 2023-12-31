package ec.edu.espe.arquitectura.banquito.controller;

import java.util.List;

import ec.edu.espe.arquitectura.banquito.dto.ClientRS;
import ec.edu.espe.arquitectura.banquito.model.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ec.edu.espe.arquitectura.banquito.dto.GroupCompanyMemberRQ;
import ec.edu.espe.arquitectura.banquito.dto.GroupCompanyRQ;
import ec.edu.espe.arquitectura.banquito.dto.GroupCompanyRS;
import ec.edu.espe.arquitectura.banquito.model.GroupCompany;
import ec.edu.espe.arquitectura.banquito.service.GroupCompanyService;

@RestController
@RequestMapping("/api/v2/companies")
@CrossOrigin
public class CompanyController {
    private final GroupCompanyService groupCompanyService;

    public CompanyController(GroupCompanyService groupCompanyService) {
        this.groupCompanyService = groupCompanyService;
    }

    @GetMapping("/{groupName}")
    public ResponseEntity<GroupCompanyRS> obtainByGroupName(
            @PathVariable(name = "groupName") String groupName) {
        try {
            GroupCompanyRS company = this.groupCompanyService.obtainCompanyByGroupName(groupName);
            return ResponseEntity.ok(company);
        } catch (RuntimeException rte) {
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping("/membersById/{groupName}")
    public ResponseEntity<List<ClientRS>> obtainMembersByGroupName(
            @PathVariable(name = "groupName") String groupName) {
        try {
            List<ClientRS> members = this.groupCompanyService.listMembersByCompany(groupName);
            return ResponseEntity.ok(members);
        } catch (RuntimeException rte) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/listCompanies")
    public ResponseEntity<List<GroupCompanyRS>> obtainAllCompanies() {
        try {
            List<GroupCompanyRS> companies = this.groupCompanyService.obtainAllCompanies();
            return ResponseEntity.ok(companies);
        } catch (RuntimeException rte) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<GroupCompany> clientCreate(@RequestBody GroupCompanyRQ company) {
        try {
            GroupCompany companyRS = this.groupCompanyService.companyCreate(company);
            return ResponseEntity.ok(companyRS);
        } catch (RuntimeException rte) {
            return ResponseEntity.badRequest().build();

        }
    }

    @PutMapping("/addMember/{groupName}")
    public ResponseEntity<GroupCompany> addMembers(@RequestBody List<GroupCompanyMemberRQ> membersRQ,
            @PathVariable(name = "groupName") String groupName) {
        try {
            GroupCompany companyRS = this.groupCompanyService.addMember(groupName, membersRQ);
            return ResponseEntity.ok(companyRS);
        } catch (RuntimeException rte) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updateCompany/{uniqueKey}")
    public ResponseEntity<GroupCompany> companyUpdate(@RequestBody GroupCompanyRQ company,
            @PathVariable(name = "uniqueKey") String uniqueKey) {
        try {
            GroupCompany companyRS = this.groupCompanyService.updateCompany(company, uniqueKey);
            return ResponseEntity.ok(companyRS);
        } catch (RuntimeException rte) {
            return ResponseEntity.badRequest().build();

        }
    }

    @PutMapping("/deleteCompany/{uniqueKey}")
    public ResponseEntity<GroupCompany> companyDelete(@PathVariable(name = "uniqueKey") String uniqueKey) {
        try {
            GroupCompany companyRS = this.groupCompanyService.deleteCompany(uniqueKey);
            return ResponseEntity.ok(companyRS);
        } catch (RuntimeException rte) {
            return ResponseEntity.badRequest().build();

        }
    }

    @PutMapping("/updateMember/{companyId}/{clientId}")
    public ResponseEntity<String> updateMember(@RequestBody GroupCompanyMemberRQ companyRQ,
            @PathVariable(name = "companyId") String companyId,
            @PathVariable(name = "clientId") String clientId) {
        try {
            this.groupCompanyService.updateMember(companyId, clientId, companyRQ);
            return ResponseEntity.ok("Miembro actualizado");
        } catch (RuntimeException rte) {
            return ResponseEntity.badRequest().build();
        }
    }
}
