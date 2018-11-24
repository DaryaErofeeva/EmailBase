// TODO: need to remove after add receiving real response
export const json = [
    {
        "id": 0,
        "surname": "Фамилия",
        "name": "Имя",
        "patronymic": "Отчество",
        "code": "Код",
        "login": "Dinnage1Ethe",
        "initPassword": "T3McE5Yi",
        "imagine": false,
        "office": false,
        "errorField": "login",
        "errorMessage": "Such login already exists in DB",
        "groupDTO": {
            "id": 0,
            "studyLevelDTO": {
                "id": 0,
                "levelId": 6,
                "name": "STUDY_LEVEL_NAME_STUB"
            },
            "facultyDTO": {
                "id": 0,
                "facultyId": 4,
                "name": "FACULTY_LEVEL_NAME_STUB",
                "abbr": "FACULTY_ABBR_STUB"
            },
            "specializationDTO": {
                "id": 0,
                "specializationId": 12,
                "name": "SPECIALIZATION_NAME_STUB",
                "specialityDTO": {
                    "id": 0,
                    "specialityId": 121,
                    "name": "SPECIALITY_NAME_STUB"
                }
            },
            "year": 17,
            "num": 1
        },
        "budget": false
    },
    {
        "id": 0,
        "surname": "Фамилия",
        "name": "Имя",
        "patronymic": "Отчество",
        "code": "Код",
        "login": "Servis1Shanon",
        "initPassword": "FO8DrdLn",
        "imagine": false,
        "office": false,
        "errorField": "code",
        "errorMessage": "Such code already exists in DB",
        "groupDTO": {
            "id": 0,
            "studyLevelDTO": {
                "id": 0,
                "levelId": 6,
                "name": "STUDY_LEVEL_NAME_STUB"
            },
            "facultyDTO": {
                "id": 0,
                "facultyId": 4,
                "name": "FACULTY_LEVEL_NAME_STUB",
                "abbr": "FACULTY_ABBR_STUB"
            },
            "specializationDTO": {
                "id": 0,
                "specializationId": 12,
                "name": "SPECIALIZATION_NAME_STUB",
                "specialityDTO": {
                    "id": 0,
                    "specialityId": 121,
                    "name": "SPECIALITY_NAME_STUB"
                }
            },
            "year": 17,
            "num": 1
        },
        "budget": false
    }
];

$(document).ready(() => {
    (new ParseErrorPageHandler()).init();
});

class ParseErrorPageHandler {
    /**
     * Method initialize class
     */
    init() {
        this.handleResponse();
        //this.sendDataRequest();
        this.initListeners();
    }

    /**
     * Method initialize event listeners
     */
    initListeners() {

    }

    /**
     * Method send request and on
     * success show response result
     */
    sendDataRequest() {
        $.ajax({
            url: '/parse/parse/error',
            type: 'get',
            data: {},
            contentType: 'application/json',
            dataType: 'json',
            success: (data) => {
                // TODO: change 'json' to 'data' after receiving real response
                this.response = json;
            }
        });
    }

    handleResponse() {

    }
}