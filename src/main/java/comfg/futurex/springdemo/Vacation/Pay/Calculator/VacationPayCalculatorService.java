package comfg.futurex.springdemo.Vacation.Pay.Calculator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VacationPayCalculatorService {

    @RequestMapping("/")
    public String index(){
        return ("Hello World");
    }


    @RequestMapping("/calculateVacationPay")
    public double calculateVacationPay(@RequestParam double averageSalary, @RequestParam long numberOfVacationDays){
        return averageSalary / 29.3 * numberOfVacationDays; //29,3 — это среднее количество дней в месяце
    }

    @RequestMapping("/calculateOnTheInterval")
    public double calculateOnTheInterval(@RequestParam double averageSalary, @RequestParam String startDate, @RequestParam String endDate){

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        if( start.isAfter(end)){
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        long workingDays = 0;
        LocalDate currentDate = start;
        while (!currentDate.isAfter(end)) {
            if (!(currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                workingDays++;
            }
            currentDate = currentDate.plusDays(1);
        }
        return calculateVacationPay(averageSalary, workingDays);
    }

}
