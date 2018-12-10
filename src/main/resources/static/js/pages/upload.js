$(document).ready(() => {
    (new UploadPageHandler()).init();
});

class UploadPageHandler {
    init() {
        this.$fileInput = $('.custom-file-input');
        this.$submitBtn = $('.js-upload-btn');
        this.initListeners();
    }

    initListeners() {
        this.$fileInput.on('change', () => this.fileValidationCallBack());
        this.$submitBtn.on('mouseover', () => this.fileValidationCallBack());

      $('input[type="file"]').change(function(e){
        const fileName = e.target.files[0].name;
        $('.custom-file-label').html(fileName);
      });
    }

    fileValidationCallBack() {
        const $msgBox = $('.js-msg-box');
        const csvTypes = ["text/comma-separated-values", "text/csv", "application/csv", "application/vnd.ms-excel"];
        const files = this.$fileInput[0].files;
        let isValid = false;
        let file = '';

        if (files.length) {
            file = this.$fileInput[0].files[0];
            isValid = file.size !== 0 && csvTypes.indexOf(file.type) > -1;
        }

        this.$submitBtn.prop('disabled', !isValid);

        !isValid
            ? this.showMessage($msgBox, 'Ошибка, файл пустой или не CSV!')
            : this.hideMessage($msgBox)
    }

    showMessage($selector, msg, type = 'danger') {
        $selector
            .removeClass('d-none')
            .addClass(`alert-${type}`)
            .html(msg);
    }

    hideMessage($selector, type = 'danger') {
        $selector
            .addClass('d-none')
            .removeClass(`alert-${type}`)
            .empty();
    }
}