package tr1nks.service.logic.impl;

import org.springframework.stereotype.Service;
import tr1nks.service.logic.TransliterateService;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransliterateServiceImpl implements TransliterateService {
    private static final Map<Character, String> charMap = new HashMap<>();

    static {
        charMap.put('А', "A");
        charMap.put('Б', "B");
        charMap.put('В', "V");
        charMap.put('Г', "H");
        charMap.put('Ґ', "G");
        charMap.put('Д', "D");
        charMap.put('Е', "E");
        charMap.put('Є', "Ye"); // в начале слова и ie в других позициях
        charMap.put('Ж', "Zh");
        charMap.put('З', "Z");
        charMap.put('И', "Y");
        charMap.put('І', "I");
        charMap.put('Ї', "Yi");// в начале слова и i в других позициях
        charMap.put('Й', "Y");// в начале слова и i в других позициях
        charMap.put('К', "K");
        charMap.put('Л', "L");
        charMap.put('М', "M");
        charMap.put('Н', "N");
        charMap.put('О', "O");
        charMap.put('П', "P");
        charMap.put('Р', "R");
        charMap.put('С', "S");
        charMap.put('Т', "T");
        charMap.put('У', "U");
        charMap.put('Ф', "F");
        charMap.put('Х', "Kh");
        charMap.put('Ц', "Ts");
        charMap.put('Ч', "Ch");
        charMap.put('Ш', "Sh");
        charMap.put('Щ', "Shch");
        //charMap.put('Ъ', "'");
        //charMap.put('Ы', "Y");
        //charMap.put('Ь', "'");
        //charMap.put('Э', "E");
        charMap.put('Ю', "Yu");//в начале и iu в других позициях
        charMap.put('Я', "Ya");//в начале и ia в других позициях
        charMap.put('а', "a");
        charMap.put('б', "b");
        charMap.put('в', "v");
        charMap.put('г', "h");
        charMap.put('ґ', "g");
        charMap.put('д', "d");
        charMap.put('е', "e");
        charMap.put('є', "ie"); // в других позициях
        charMap.put('ж', "zh");
        charMap.put('з', "z");
        charMap.put('и', "y");
        charMap.put('і', "i");
        charMap.put('ї', "i");// в других позициях
        charMap.put('й', "i");// в других позициях
        charMap.put('к', "k");
        charMap.put('л', "l");
        charMap.put('м', "m");
        charMap.put('н', "n");
        charMap.put('о', "o");
        charMap.put('п', "p");
        charMap.put('р', "r");
        charMap.put('с', "s");
        charMap.put('т', "t");
        charMap.put('у', "u");
        charMap.put('ф', "f");
        charMap.put('х', "kh");
        charMap.put('ц', "ts");
        charMap.put('ч', "ch");
        charMap.put('ш', "sh");
        charMap.put('щ', "shch");
        charMap.put('ь', "");
        charMap.put('\'', "");
        charMap.put('`', "");
        //charMap.put('ъ', "'");
        //charMap.put('ы', "y");
        //charMap.put('ь', "'");
        //charMap.put('э', "e");
        charMap.put('ю', "iu");//в других позициях
        charMap.put('я', "ia");//в других позициях
    }

    @Override
    public String generate(String inpStr) {
        StringBuilder replStr = new StringBuilder();
        for (int i = 0; i < inpStr.length(); i++) {
            Character chr = inpStr.charAt(i);
            String charFromMap = charMap.get(chr);
            if (charFromMap == null) {
                replStr.append(chr);
            } else {
                replStr.append(charFromMap);
            }
        }
        return replStr.toString();
    }
}
