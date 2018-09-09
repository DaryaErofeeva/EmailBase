;
/**
 * выделить или снять выделение со всех checkbox
 * @param parentCheckbox
 * @param checkClass
 */
function genSelect(parentCheckbox, checkClass) {
    var arr = document.getElementsByClassName(checkClass);
    if (parentCheckbox.checked && arr.length > 0) {
        for (var i = 0; i < arr.length; i++) {
            arr[i].checked = true;
            // arr[i].onclick();
        }
    } else {
        for (var q = 0; q < arr.length; q++) {
            arr[q].checked = false;
            // arr[q].onclick();
        }
    }
}