package tr1nks.service.logic;

public interface CredentialGenerationService {
    String generatePassword(int lenthOfPass);

    String createLogin(String surname, String name);
}
