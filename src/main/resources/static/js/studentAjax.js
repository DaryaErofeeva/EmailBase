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

function createCheckboxTableCell(value, cellId) {
    var cell = createTableCell(cellId);
    var checkbox = $('<input>', {type: 'checkbox', checked: value});
    cell.append(checkbox);
    return cell;
}


$.fn.extend({
    parseStudentTableRow: function () {
        if ($(this).length > 0) {
            var student = $(this)[0];
            var rowId = student.id;
            var row = $('<tr>', {id: "student-row-" + rowId});
            var cellIdPrefix = "student-row-" + rowId + "-";
            $(row).append(createCheckboxTableCell(student.checked, cellIdPrefix + 'checked'));
            // $(row).append($(student.id).parseCheckboxTableCell(student.id, 'id'));
            return row;
        }
    }
});


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
            $.each(studentPageModel.students, function (index, item) {
                $(table).append($(item).parseStudentTableRow());
            });
        });
    })
});


