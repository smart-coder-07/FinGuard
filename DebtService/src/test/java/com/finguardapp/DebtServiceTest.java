package com.finguardapp;
import org.junit.jupiter.api.BeforeEach;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.debt.Debt1Application;
import com.debt.dto.DebtDto;
import com.debt.dto.User;
import com.debt.exception.DebtCustomException;
import com.debt.model.Debt;
import com.debt.repository.DebtRepository;
import com.debt.sevice.DebtService;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = Debt1Application.class)
@ExtendWith(SpringExtension.class)  // Required for Spring TestContext framework
class DebtServiceTest {

    @Autowired
    private DebtService debtService;

    @Autowired
    private DebtRepository debtRepository;


    @Test
    void testAddDebt_Success() {
        // Create debtDto with a valid user ID
        DebtDto debtDto = new DebtDto();
        debtDto.setUserId(123); // Ensure this is a valid user ID
        debtDto.setDept_Type("Personal Loan");
        debtDto.setDue_Date("2024-12-31");
        debtDto.setPrinciple_Amount(5000);
        debtDto.setRemaining_Amount(5000);
        debtDto.setDuration_In_Months(12);
        debtDto.setInterest_Rate(5.0);

        // Attempt to add debt and expect a DebtCustomException if user is not found
        try {
            debtService.addDebt(debtDto);
            fail("Expected DebtCustomException to be thrown");
        } catch (DebtCustomException e) {
            assertEquals("User not found for ID: 123", e.getMessage());
        }
    }


    @Test
    void testCalculateEMI() {
        Debt debt = new Debt();
        debt.setPrinciple_Amount(10000);
        debt.setInterest_Rate(10);
        debt.setDuration_In_Months(12);

        double emi = debtService.calculateEMI(debt);

        // Calculate expected EMI manually for comparison
        double expectedEMI = (10000 * 0.008333 * Math.pow(1 + 0.008333, 12)) / 
                             (Math.pow(1 + 0.008333, 12) - 1);

        assertEquals(expectedEMI, emi, 0.01);
    }
    
    @Test
    void testCalculateTotalInterest() {
        // Create a Debt object with sample data
        Debt debt = new Debt();
        debt.setPrinciple_Amount(10000.0);  // Principle amount of 10,000
        debt.setInterest_Rate(10.0);        // Interest rate of 10%
        debt.setDuration_In_Months(12);     // Duration of 12 months

        // Calculate total interest using the method
        double totalInterest = debtService.calculateTotalInterest(debt);

        // Expected EMI calculation for comparison
        double emi = debtService.calculateEMI(debt);
        double expectedInterest = emi * debt.getDuration_In_Months() - debt.getPrinciple_Amount();

        // Assert that the calculated total interest is correct
        assertEquals(expectedInterest, totalInterest, 0.01, "The total interest calculated is incorrect.");
    }

    @Test
    void testGenerateRepaymentSchedule() {
        Debt debt = new Debt();
        debt.setPrinciple_Amount(10000);
        debt.setInterest_Rate(10);
        debt.setDuration_In_Months(12);
        debt.setRemaining_Amount(10000);

        List<String> schedule = debtService.generateRepaymentSchedule(debt);

        // Check that the schedule is not empty
        assertFalse(schedule.isEmpty());

        // Verify the first entry to check correctness
        assertTrue(schedule.get(0).contains("Month: 1"));
    }

    @Test
    void testFindDebtsByUserId() {
        // Create and save debts with userId 123
        Debt debt1 = new Debt();
        debt1.setUserId(123);
        debtRepository.save(debt1);

        Debt debt2 = new Debt();
        debt2.setUserId(123);
        debtRepository.save(debt2);

        // Create and save a debt with userId 456 (to ensure we test different userId)
        Debt debt3 = new Debt();
        debt3.setUserId(456);
        debtRepository.save(debt3);

        // Fetch debts by userId 123
        List<Debt> debts = debtService.findDebtsByUserId(123);


        // Assert that all debts belong to userId 123
        assertTrue(debts.stream().allMatch(debt -> debt.getUserId() == 123),
                "All debts should have userId 123");
    }
    
 

    @Test
    void testFindDebtById_Success() {
    	Debt debt1 = new Debt();
        debt1.setDept_Type("Personal Loan");
        debt1.setPrinciple_Amount(1000.0);
        debt1.setRemaining_Amount(1000.0);
        debt1.setDuration_In_Months(12);
        debt1.setInterest_Rate(5.0);
        debt1.setUserId(123); // Set userId for identification
        debtRepository.save(debt1);
        // Find debt by ID
        Debt foundDebt = debtService.findDebtById(debt1.getId());

        // Assert that the debt is found and details match
        assertNotNull(foundDebt, "Debt should not be null");
        assertEquals(debt1.getId(), foundDebt.getId(), "Debt ID should match");
        assertEquals(debt1.getDept_Type(), foundDebt.getDept_Type(), "Debt type should match");
    }


    @Test
    void testFindAll() {
        // Create and save a new debt
        Debt debt1 = new Debt();
        debt1.setDept_Type("Personal Loan");
        debt1.setPrinciple_Amount(1000.0);
        debt1.setRemaining_Amount(1000.0);
        debt1.setDuration_In_Months(12);
        debt1.setInterest_Rate(5.0);
        debt1.setUserId(123); // Set userId for identification
        debtRepository.save(debt1);

        // Retrieve all debts
        List<Debt> allDebts = debtService.findAll();

        // Check if the list is not empty
        assertNotNull(allDebts, "Debts list should not be null");

        // Debug: Print out the list of all debts
        allDebts.forEach(debt -> System.out.println("Debt in list: " + debt));

    }

}


