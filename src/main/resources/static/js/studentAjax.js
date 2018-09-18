;

function sendAjax(url, method, data, handler) {
    $.ajax({
        url: url,
        type: method,
        data: data,
        dataType: 'json',
        success: handler
    });
}

function createTableCell(cellId) {
    return $('<td>', {id: cellId});
}

function createTableRow(rowId) {
    return $('<tr>', {id: rowId});
}

function createHiddenInput(value, inputId) {
    return $('<input>', {id: inputId, type: 'hidden', value: value, class: 'd-none'});
}

function createCheckboxTableCell(value, cellId) {
    var cell = createTableCell(cellId);
    var checkbox = $('<input>', {type: 'checkbox', checked: value, class: 'form-control input-sm'});
    cell.append(checkbox);
    return cell;
}

function createTextInputTableCell(value, cellId) {
    var cell = createTableCell(cellId);
    var input = $('<input>', {type: 'text', value: value, class: 'form-control input-sm'});
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
    $(row).append(createHiddenInput(student.id, cellIdPrefix + 'id'));
    // $(row).append(createTextTableCell(rowIndex, cellIdPrefix + 'num'));
    $(row).append(createCheckboxTableCell(student.checked, cellIdPrefix + 'checked'));
    $(row).append(createTextInputTableCell(student.surname, cellIdPrefix + 'surname'));
    $(row).append(createTextInputTableCell(student.name, cellIdPrefix + 'name'));
    $(row).append(createTextInputTableCell(student.patronymic, cellIdPrefix + 'patronymic'));
    $(row).append(createTextInputTableCell(student.groupEntity, cellIdPrefix + 'patronymic'));
    $(row).append(createTextInputTableCell(student.code, cellIdPrefix + 'code'));
    $(row).append(createTextInputTableCell(student.login, cellIdPrefix + 'login'));
    $(row).append(createTextInputTableCell(student.initPassword, cellIdPrefix + 'initPassword'));
    $(row).append(createCheckboxTableCell(student.imagine, cellIdPrefix + 'imagine'));
    $(row).append(createCheckboxTableCell(student.office, cellIdPrefix + 'office'));
    $(row).append(createCheckboxTableCell(student.budget, cellIdPrefix + 'budget'));


    return row;
}

$(document).ready(function (event) {
    $("#test-button").click(function (event) {
        $("#test-button").myfunction();
    });
    $("#student-page-form").submit(function (event) {
        event.preventDefault();
        var url = $(this).attr("action");
        var method = $(this).attr("method").toUpperCase();
        var data = $(this).serialize();
        sendAjax(url, method, data, function (studentPageModel) {
            var table = $("#person-table");
            $(table).empty();
            $.each(studentPageModel.students, function (index, student) {
                $(table).append(createStudentTableRow(index + 1, student));
            });
        });
    })
});


