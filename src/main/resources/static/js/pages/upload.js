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
    }

    fileValidationCallBack() {
        const $msgBox = $('.js-msg-box');
        const files = this.$fileInput[0].files;
        let isValid = false;
        let file = '';

        if (files.length) {
            file = this.$fileInput[0].files[0];
            isValid = file.size !== 0 && file.type === 'text/csv';
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