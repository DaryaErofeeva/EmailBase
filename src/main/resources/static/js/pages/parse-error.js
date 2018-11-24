$(document).ready(() => {
    (new ParseErrorPageHandler()).init();
});

class StudentHelper {
    constructor(students) {
        this.students = students;
    }

    getStudentById(id) {
        let result = {};

        this.students.forEach((student) => {
            if (student.id === id) {
                result = student;
            }
        });

        return result;
    }

    setStudent(student) {
        this.removeStudentById(student.id);
        this.students.push(student);
    }

    removeStudentById(id) {
        let result = [];

        this.students.forEach((student) => {
            if (student.id !== id) {
                result.push(student);
            }
        });

        this.students = result;
    }
}

class Paginator {
    constructor(
        itemsCount,
        resPerPage = 1,
        currentPage = 1,
        paginatorClass = 'pagination',
        pageItemClass = 'page-item'
    ) {
        this.itemsCount = itemsCount;
        this.resultsPerPage = resPerPage;
        this.currentPage = currentPage;

        this.pageItemClass = pageItemClass;

        this.$paginator = $(`.${paginatorClass}`);
    }

    init() {
        this.render();
    }

    render() {
        this.pagesCount = this.itemsCount / this.resultsPerPage;

        if (this.pagesCount > 1) {
            for (let pageNum = 1; pageNum < this.pagesCount + 1; pageNum++) {
                this.$paginator.append(`
          <li data-id="${pageNum}" class="${this.pageItemClass}">
            <a class="page-link">${pageNum}</a>
          </li>
        `);
            }

            this.changePageLabel(this.currentPage);
        } else {
            this.$paginator.empty();
        }
    }

    calculate(page) {
        let from = (this.itemsCount / this.pagesCount) * --page;
        let to = from + this.resultsPerPage;

        return {
            from: from,
            to: --to,
        };
    }

    changePageLabel(pageNumber) {
        $(`.${this.pageItemClass}`).removeClass('active text-white');
        $(`.${this.pageItemClass}[data-id="${pageNumber}"]`).addClass('active text-white');
    }
}

class ParseErrorPageHandler {
    /**
     * Method initialize class
     */
    init() {
        this.sendGetDataRequest();

        this.studentHelper = new StudentHelper(this.response);
        this.paginator = new Paginator(this.response.length);

        this.paginator.init();
        this.$tableBody = $('.error-students-table');

        this.$continueBtn = $('.continue-btn');
        this.rowClass = 'error-student-row';
        this.errorFieldClass ='js-error-field';

        this.hasErrorClass = 'alert alert-danger';

        this.currentPage = 1;

        this.renderPage(this.currentPage);
        this.initListeners();
    }

    renderPage(pageNumber) {
        this.renderTableElements(pageNumber);
        this.showErrorCells();
    }

    renderTableElements(page) {
        let html = '';
        const fromTo = this.paginator.calculate(page);

        this.$tableBody.empty();
        this.response.forEach((student, index) => {
            if (index >= fromTo.from && index <= fromTo.to)
                html += `
          <tr data-id="${student.id}" class="${this.rowClass}">
              <th scope="row">${student.id}</th>
              <td>${student.surname}</td>
              <td>${student.name}</td>
              <td>${student.patronymic}</td>
              <td data-field="code" class="readonly js-error-field error-input-code border text-center">${student.code}</td>
              <td data-field="login" class="readonly js-error-field error-input-login border text-center">${student.login}</td>
          </tr>   
        `;
        });

        this.$tableBody.html(html);
    }

    /**
     * Method initialize event listeners
     */
    initListeners() {
        $(document).on('click', (e) => {
            const $target = $(e.target);

            if (!$target.hasClass('edit-input')) {
                this.handleEditInput();
            }
        });

        this.$tableBody.on('click', `.${this.errorFieldClass}`, (e) => {
            e.preventDefault();
            e.stopPropagation();

            const $field = $(e.currentTarget);
            const text = $field.html();
            const isInputType = text.indexOf('<input') > -1;

            if (!isInputType && !$field.hasClass('readonly')) {
                $field.html(`
            <input type="text" class="form-control col edit-input" data-origin-text="${text}" value="${text}"/>
        `);
            }
        });

        this.$continueBtn.on('mouseover', (e) => {
            $(e.currentTarget).prop('disabled', this.errorFieldsCount);
        });

        this.$continueBtn.on('click', () => {
            this.sendPostDataRequest();
        });

        $(`.page-link`).on('click', (e) => {
            this.currentPage = $(e.currentTarget).html();
            this.paginator.changePageLabel(this.currentPage);
            this.renderPage(this.currentPage);
        });
    }

    showErrorCells() {
        this.response.forEach((student) => {
            const errorField = student.errorField;
            const errorMessage = student.errorMessage;

            if (errorField.length || errorMessage.length ) {
                const wrapperSelector = `.error-student-row[data-id="${student.id}"]`;

                $(`${wrapperSelector} .error-input-${errorField}`)
                    .prop('title', errorMessage)
                    .removeClass('readonly')
                    .addClass(this.hasErrorClass);
            }
        })
    }

    handleEditInput() {
        $.each($('.edit-input'), (index, item) => {
            const $item = $(item);
            const $row = $item.closest(`.${this.errorFieldClass}`);
            const field = $row.data('field');
            let student = this.studentHelper.getStudentById($row.closest(`.${this.rowClass}`).data('id'));
            const originText = student[field];

            $row.html($item.val());

            if ($item.val() !== originText) {
                $row.removeClass(this.hasErrorClass).addClass('readonly');

                student[field] = $row.html();
                student['errorField'] = '';
                student['errorMessage'] = '';

                this.studentHelper.setStudent(student);
                this.reduceErrorFieldsCount();

            } else {
                $row.addClass(this.hasErrorClass);
            }
        });
    }

    /**
     * Reduce number of errors and
     * check their number to turn on
     * the 'Continue' button
     */
    reduceErrorFieldsCount() {
        this.errorFieldsCount--;

        if (!this.errorFieldsCount) {
            $('.continue-btn').trigger('mouseover');
        }
    }

    sendGetDataRequest() {
        $.ajax({
            url: '/parse/parse/error',
            type: 'get',
            data: {},
            contentType: 'application/json',
            dataType: 'json',
            success: (data) => {
                this.response = JSON.parse(data);
            }
        });

        this.errorFieldsCount = this.response.length;
    }

    sendPostDataRequest() {
        $.ajax({
            url: '/parse/parse/error',
            type: 'post',
            data: JSON.stringify(this.response),
            contentType: 'application/json',
            dataType: 'json'
        });
    }
}