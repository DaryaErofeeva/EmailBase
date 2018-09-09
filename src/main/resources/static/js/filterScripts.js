;

function addRemoveThis(inp) {
    $(function () {
        var div = $(inp).closest("div.dropdown").find("div.panel-heading");
        var abbr = inp.getAttribute("data-abbr");
        if (inp.checked) {
            $(div).append("<span class='label label-success visible-lg-inline-block'>"+abbr+"</span>");
        } else {
            $(div).find("span:contains('"+abbr+"')").remove();
        }
    });
}