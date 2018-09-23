;

function sendAjax(url, method, data, handler) {
    $.ajax({
        url: url,
        type: method,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: 'json',
        success: handler
    });
}



$.fn.extend({
    serializeFormJSON: function () {
        var jsonObj = {};
        console.log(JSON.stringify(this.serialize()));
        var formArr = this.serializeArray();
        $.each(formArr, function () {
            // if ()
            //     if (jsonObj[this.name]) {
            //         if (!jsonObj[this.name].push) {
            //             jsonObj[this.name] = [jsonObj[this.name]];
            //         }
            //         jsonObj[this.name].push(this.value || '');
            //     } else {
            //         jsonObj[this.name] = this.value || '';
            //     }
        });
        var unindexed_array = this.serializeArray();
        var indexed_array = {};

        $.map(unindexed_array, function(n, i){
            indexed_array[n['name']] = n['value'];
        });

        return indexed_array;

        // return jsonObj;
    }
});

function createTableCell(cellId) {
    return $('<td>', {id: cellId});
}

function createTableRow(rowId) {
    return $('<tr>', {id: rowId});
}

function createHiddenInput(value, inputId, inputName) {
    return $('<input>', {id: inputId, type: 'hidden', name: inputName, value: value, class: 'd-none'});
}

function createCheckboxTableCell(value, cellId, inputName) {
    var cell = createTableCell(cellId);
    var checkbox = $('<input>', {type: 'checkbox', name: inputName, checked: value, class: 'form-control input-sm'});
    cell.append(checkbox);
    return cell;
}

function createTextInputTableCell(value, cellId, inputName) {
    var cell = createTableCell(cellId);
    var input = $('<input>', {type: 'text', name: inputName, value: value, class: 'form-control input-sm'});
    cell.append(input);
    return cell;
}

function createTextTableCell(value, cellId) {
    var cell = createTableCell(cellId);
    cell.append(value);
    return cell;
}

function createStudentTableRow(rowIndex, student) {
    var rowId = student.id;
    var row = createTableRow("student-row-" + rowId);
    var cellIdPrefix = "student-row-" + rowId + "-";
    $(row).append(createHiddenInput(student.id, cellIdPrefix + 'id', 'students[' + rowIndex + '][id]'));
    // $(row).append(createTextTableCell(rowIndex, cellIdPrefix + 'num'));
    $(row).append(createCheckboxTableCell(student.checked, cellIdPrefix + 'checked', 'students[' + rowIndex + '][checked]'));
    $(row).append(createTextInputTableCell(student.surname, cellIdPrefix + 'surname', 'students[' + rowIndex + '][surname]'));
    $(row).append(createTextInputTableCell(student.name, cellIdPrefix + 'name', 'students[' + rowIndex + '][name]'));
    $(row).append(createTextInputTableCell(student.patronymic, cellIdPrefix + 'patronymic', 'students[' + rowIndex + '][patronymic]'));
    $(row).append(createTextInputTableCell(student.groupEntity, cellIdPrefix + 'group', 'students[' + rowIndex + '][groupEntity]'));
    $(row).append(createTextInputTableCell(student.code, cellIdPrefix + 'code', 'students[' + rowIndex + '][code]'));
    $(row).append(createTextInputTableCell(student.login, cellIdPrefix + 'login', 'students[' + rowIndex + '][login]'));
    $(row).append(createTextInputTableCell(student.initPassword, cellIdPrefix + 'initPassword', 'students[' + rowIndex + '][initPassword]'));
    $(row).append(createCheckboxTableCell(student.imagine, cellIdPrefix + 'imagine', 'students[' + rowIndex + '][imagine]'));
    $(row).append(createCheckboxTableCell(student.office, cellIdPrefix + 'office', 'students[' + rowIndex + '][office]'));
    $(row).append(createCheckboxTableCell(student.budget, cellIdPrefix + 'budget', 'students[' + rowIndex + '][budget]'));
    return row;
}

function processStudentPageModel(studentPageModel) {
    if (studentPageModel) {
        console.log(studentPageModel);
        var table = $("#person-table");
        var tbody = $(table).find("tbody");
        $("#page-number").val(studentPageModel.page);
        $(tbody).empty();
        $.each(studentPageModel.students, function (index, student) {
            $(tbody).append(createStudentTableRow(index + 1, student));
        });
    }
}

$(document).ready(function (event) {
    $("#student-page-form").submit(function (event) {
        event.preventDefault();
        var url = $(this).attr("action");
        var method = $(this).attr("method").toUpperCase();
        var data = $(this).serialize();
        data = $(this).serializeFormJSON();
        sendAjax(url, method, data, processStudentPageModel);
    });
});


