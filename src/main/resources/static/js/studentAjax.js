function sendAjax(url,method,data,handler){
  $.ajax({
    url: url,
    type: method,
    data: data,
    success: handler
  });
}

(function( $ ){
    $.fn.studentTableRow = function() {
      var row = $('<tr>', {id: $(this)[0].id});
      $.each($(this)[0],function(index,item){
        $(row).append($('<td>',{text: item}));
      });
      // $(row).append($('<td>',{text: $(this)[0].id}));
      // $(row).append($('<td>',{text: $(this)[0].surname}));
      // $(row).append($('<td>',{text: $(this)[0].name}));
      // $(row).append($('<td>',{text: $(this)[0].patronymic}));
      // $(row).append($('<td>',{text: $(this)[0].code}));
      // $(row).append($('<td>',{text: $(this)[0].login}));
      return row;
  }
})( jQuery );

$(document).ready(function(event) {
  $("#test-button").click(function(event){
    $("#test-button").myfunction();
  });
  $("#student-page-form").submit(function(event){
    event.preventDefault();
    var url= $(this).attr("action");
    var method= $(this).attr("method").toUpperCase();
    var data = $(this).serialize();
    sendAjax(url, method, data, function(returned){
      var table = $("#person-table");
      $(returned).each(function(index, item) {
        $(table).append($(item).studentTableRow());
      });
    });
  })
});
