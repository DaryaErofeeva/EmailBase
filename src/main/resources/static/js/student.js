$.fn.extend({
    serializeFormJSON() {
        const notIndexedArray = this.serializeArray();
        let indexedArray = {};

        $.map(notIndexedArray, (array) => {
            indexedArray[array['name']] = array['value'];
        });

        return indexedArray;
    }
});

$(document).ready(() => {
    const $dataReader = $('.root');
    const jsonFromBack = $dataReader.html();

    $dataReader.empty();

    (new StudentPageHandler(jsonFromBack)).init();
});

class StudentPageHandler {
    /**
     * StudentPageHandler constructor
     * @param jsonFromBack
     */
    constructor(jsonFromBack) {
        this.jsonFromBack = jsonFromBack;
        this.sendRequest('/person/student/json', 'post', {});
    }

    /**
     * Send request to back-end for students
     *
     * @param url
     * @param method
     * @param data
     * @param successCallback
     */
    sendRequest(url, method, data, successCallback) {
        $.ajax({
            url: url,
            type: method,
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: 'json',
            success: (data) => {
                this.processStudentPageModel(data);
            }
        });
    }

    /**
     * Method initialize page handler
     */
    init() {
        this.initSelectors();
        this.initListeners();

        this.processStudentPageModel(this.jsonFromBack);
    }

    /**
     * Method initialize all class properties
     */
    initSelectors() {
        this.$studentPageForm = $('#student-page-form');
        this.$tableBody = $('#person-table tbody');
        this.$pageNumber = $('#page-number');
    }

    /**
     * Method initialize event listeners
     */
    initListeners() {
        this.$studentPageForm.on('submit', (e) => {
            const url = $(e.target).attr('action');
            const method = $(e.target).attr('method').toUpperCase();
            const data = $(e.target).serializeFormJSON();

            this.sendRequest(url, method, data, this.processStudentPageModel);
        });
    }

    /**
     * Handle all received students
     * from JSON and append to HTML
     */
    processStudentPageModel(studentPageModel) {
        this.studentPageModel = studentPageModel;

        if (this.studentPageModel) {
            this.$pageNumber.val(this.studentPageModel.page);
            this.$tableBody.empty();

            $.each(this.studentPageModel.students, (idx, student) => {
                this.$tableBody.append(this.createStudentTableRow(idx + 1, student));
            });
        }
    }

    /**
     * Table cell builder
     *
     * @param cellId
     * @returns {jQuery.fn.init|jQuery|HTMLElement}
     */
    createTableCell(cellId) {
        return $('<td>', {id: cellId});
    }

    /**
     * Table row builder
     *
     * @param rowId
     * @returns {jQuery.fn.init|jQuery|HTMLElement}
     */
    createTableRow(rowId) {
        return $('<tr>', {id: rowId});
    }

    /**
     * Hidden input builder
     *
     * @param value
     * @param inputId
     * @param inputName
     * @returns {jQuery.fn.init|jQuery|HTMLElement}
     */
    createHiddenInput(value, inputId, inputName) {
        return $('<input>', {id: inputId, type: 'hidden', name: inputName, value: value, class: 'd-none'});
    }

    /**
     * Table cell with checkbox builder
     *
     * @param value
     * @param cellId
     * @param inputName
     * @returns {*|void}
     */
    createCheckboxTableCell(value, cellId, inputName) {
        const cell = this.createTableCell(cellId);
        const checkbox = $('<input>', {type: 'checkbox', name: inputName, checked: value, class: 'input-sm'});

        return cell.append(checkbox);
    }

    /**
     * Table cell with input builder
     *
     * @param value
     * @param cellId
     * @param inputName
     * @returns {*|void}
     */
    createTextInputTableCell(value, cellId, inputName) {
        const cell = this.createTableCell(cellId);
        const input = $('<input>', {type: 'text', name: inputName, value: value, class: 'form-control input-sm'});

        return cell.append(input);
    }

    /**
     * Table assembler
     *
     * @param rowIndex
     * @param student
     * @returns {jQuery.fn.init|jQuery|HTMLElement}
     */
    createStudentTableRow(rowIndex, student) {
        const rowId = student.id;
        const $row = $(this.createTableRow(`student-row-${rowId}`));
        const cellIdPrefix = `student-row-${rowId}-`;

        $row
            .append(this.createHiddenInput(student.id, `${cellIdPrefix}id`, `students[${rowIndex}][id]`))
            .append(this.createCheckboxTableCell(student.checked, `${cellIdPrefix}checked` + `students[${rowIndex}][checked]`))
            .append(this.createTextInputTableCell(student.surname, `${cellIdPrefix}surname`, `students[${rowIndex}][[surname]`))
            .append(this.createTextInputTableCell(student.name, `${cellIdPrefix}name`, `students[${rowIndex}][name]`))
            .append(this.createTextInputTableCell(student.patronymic, `${cellIdPrefix}patronymic`, `students[${rowIndex}][patronymic]`))
            .append(this.createTextInputTableCell(student.groupEntity, `${cellIdPrefix}group`, `students[${rowIndex}][groupEntity]`))
            .append(this.createTextInputTableCell(student.code, `${cellIdPrefix}code`, `students[${rowIndex}][code]`))
            .append(this.createTextInputTableCell(student.login, `${cellIdPrefix}login`, `students[${rowIndex}][login]`))
            .append(this.createTextInputTableCell(student.initPassword, `${cellIdPrefix}initPassword`, `students[${rowIndex}][initPassword]`))
            .append(this.createCheckboxTableCell(student.imagine, `${cellIdPrefix}imagine`, `students[${rowIndex}][imagine]`))
            .append(this.createCheckboxTableCell(student.office, `${cellIdPrefix}office`, `students[${rowIndex}][office]`))
            .append(this.createCheckboxTableCell(student.budget, `${cellIdPrefix}budget`, `students[${rowIndex}][budget]`));

        return $row;
    }
}
