package Controller;

public class PasswordGenerator {
    
    // **JUST CALL THIS METHOD TO GENERATE THE RANDOM PASSWORD. IT WILL TAKE CARE OF EVERYTHING.
    public String generatePassword()
    {
        PasswordGeneratorEngine passwordGenerator = new PasswordGeneratorEngine.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
        String password = passwordGenerator.generate(8); // output ex.: lrU12fmM 75iwI90o
        System.out.println(password);
        return password;
    }
}
