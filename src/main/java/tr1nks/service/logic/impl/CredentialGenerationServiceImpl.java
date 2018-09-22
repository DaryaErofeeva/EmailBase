package tr1nks.service.logic.impl;


import org.springframework.stereotype.Component;
import tr1nks.service.domain.StudentService;
import tr1nks.service.logic.CredentialGenerationService;
import tr1nks.service.logic.TransliterateService;

import javax.annotation.Resource;

@Component
public class CredentialGenerationServiceImpl implements CredentialGenerationService {
    @Resource
    private TransliterateService trs;
    @Resource
    private StudentService studentService;

    @Override
    public String generatePassword(int lenthOfPass) {
        int typeOfSymbol;    //тип символов
        //массив символов, которые используются для генерации пароля:
        String[] Symbols = {
                "abcdefghijklmnopqrstuvwxyz",
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                "0123456789"};
//Строка, хранящая пароль с первой заглавной буквой:
        StringBuilder Password = new StringBuilder("" + Symbols[1].charAt((int) (Math.random() * Symbols[1].length())));
        for (int i = 0; i < lenthOfPass - 1; ++i) {
            // генерация типа символа:
            typeOfSymbol = (int) (Math.random() * Symbols.length);
            //добавление к паролю случайного символа из строки типа typeOfSymbol:
            Password.append(Symbols[typeOfSymbol].charAt((int) (Math.random() * Symbols[typeOfSymbol].length())));
        }
        return Password.toString();
    }

    @Override
    public String createLogin(String surname, String name) {
        String enSurn = trs.generate(surname);
        String enName = trs.generate(name);
        StringBuilder builder = new StringBuilder();
        builder.append(enSurn);
        int i = 1;
        do {
            builder.append(i).append(enName);
            if (studentService.testEmail(builder.toString())) {
                return builder.toString();
            }
            builder.delete(name.length(), builder.length());
            i++;
        } while (true);
    }
}
