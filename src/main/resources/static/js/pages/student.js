class StudentHelper {
  setStudents(students) {
    this.students = students;
  }

  getStudentById(id) {
    return this.students[id];
  }

  setStudent(student, index) {
    this.students[index] = student;
  }

  get() {
    return this.students;
  }

  setField(id, name, value) {
    this.students[id][name] = value;
  }

  getStudentGroupNumber(id) {
    const student = this.getStudentById(id);

    return this.getGroupNumber(student.groupDTO);
  }

  getGroupNumber(groupInfo) {
    const l = groupInfo.studyLevelDTO.levelId;
    const f = groupInfo.facultyDTO.facultyId;
    const s = groupInfo.specializationDTO.specializationId;
    const p = groupInfo.specializationDTO.specialityDTO.specialityId;
    const y = groupInfo.year;
    const n = groupInfo.num;

    return `${l}.${f}.${s}.${p}.${y}.${n}`;
  }

  setGroupNumber(id, groupNumber) {
    let student = this.getStudentById(id);
    const groupNumberArray = groupNumber.split('.');

    student.groupDTO.studyLevelDTO.levelId = groupNumberArray[0];
    student.groupDTO.facultyDTO.facultyId = groupNumberArray[1];
    student.groupDTO.specializationDTO.specializationId = groupNumberArray[2];
    student.groupDTO.specializationDTO.specialityDTO.specialityId = groupNumberArray[3];
    student.groupDTO.year = groupNumberArray[4];
    student.groupDTO.num = groupNumberArray[5];

    this.students[id] = student;
  }

  getGroups() {
    let list = [];

    this.students.forEach((item, index) => {
      list.push(this.getStudentGroupNumber(index));
    });

    return list;
  }
}
class Paginator {
  constructor(
    itemsCount,
    resPerPage = 50,
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
    this.$paginator.empty();

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

$(document).ready(() => {
    (new StudentPageHandler());
});

class StudentPageHandler {
  constructor() {
    this.INPUT_TYPES = {
      TEXT: 'text',
      SELECT: 'select',
      CHECKBOX: 'checkbox',
    };

    this.CLASS = {
      CHECKBOX_CELL: 'checkbox-field',
      TEXT_CELL: 'text-field',
      SELECT_CELL: 'select-field',
      CHECKBOX_INPUT: 'checkbox-input',
      TEXT_INPUT: 'text-input',
      SELECT_INPUT: 'select-input',
      STUDENT_ROW: 'student-row',
      FILTERS_SELECTS: 'filters-selects',
      MSG_BOX_FILTERS: 'msg-box-filters',
    };

    this.MSG = {
      INFO: 'alert alert-info',
      WARNING: 'alert alert-warning',
      ERROR: 'alert alert-danger',
    };

    this.filters = {
        faculty: [],
        group: [],
        year: []
    };
    this.getFilters();
  }

  /**
   * Method initialize page handler
   */
  init() {
    this.initDOMObjects();
    this.initListeners();

    this.initFilters();
  }

  /**
   * Method init jQuery objects
   */
  initDOMObjects() {
    this.$filtersBody = $(`.${this.CLASS.FILTERS_SELECTS}`);
    this.$tableBody = $('.students-table');

    this.$filtersApplyBtn = $('.filters-apply');
    this.$selectAllBtn = $('.select-all');
    this.$continueBtn = $('.send-updated-data');
    this.$makeArchiveBtn = $('.send-make-archive');
    this.$makeCsvEmailBtn = $('.send-make-csv-email');
    this.$makeCsvImagineBtn = $('.send-make-csv-imagine');
    this.$makeCsvOfficeBtn = $('.send-make-csv-office');
    this.$setBtns= $('.set-btn');
    this.$unsetBtns = $('.unset-btn');
  }

  /**
   * Method initialize general event listeners
   */
  initListeners() {
    this.$filtersApplyBtn.unbind('click').on('click', () => {
      this.handleFiltersApplyBtn();
    });

    this.$selectAllBtn.unbind('click').on('click', (e) => {
      if (this.students) {
        this.toggleSelectedCheckboxes(e);
      }
    });

    this.$continueBtn.unbind('click').on('click', () => {
      this.sendPostDataRequest('/person/student');
    });

    this.$makeArchiveBtn.unbind('click').on('click', () => {
      this.sendPostDataRequest('/person/zip', 'students.zip');
    });

    this.$makeCsvEmailBtn.unbind('click').on('click', () => {
    this.sendPostDataRequest('/person/email', 'emails.csv');
    });

    this.$makeCsvImagineBtn.unbind('click').on('click', () => {
      this.sendPostDataRequest('/person/imagine', 'imagines.csv');
    });

    this.$makeCsvOfficeBtn.unbind('click').on('click', () => {
      this.sendPostDataRequest('/person/office', 'offices.csv');
    });

    this.$continueBtn.unbind('mouseover').on('mouseover', () => {
      this.$continueBtn.prop('disabled', !this.students);
    });

    this.$makeArchiveBtn.unbind('mouseover').on('mouseover', () => {
      this.$makeArchiveBtn.prop('disabled', !this.students);
    });

    this.$setBtns.unbind('click').on('click', (e) => {
      this.handleManageButtonsClick(e, true);
    });

    this.$unsetBtns.unbind('click').on('click', (e) => {
      this.handleManageButtonsClick(e, false);
    });
  }

  /**
   * Method initialize table event listeners
   */
  initTableListeners() {
    $('.page-wrapper').unbind('click').on('click', `.${this.CLASS.CHECKBOX_CELL}`, (e) => {
      this.handleInputType(this.INPUT_TYPES.CHECKBOX, e);
      if ($(e.currentTarget).data('field') !== 'selected') {
        this.$continueBtn.trigger('mouseover');
      }
    });

    $('.page-wrapper').unbind('change').on('change', `.${this.CLASS.TEXT_CELL}`, (e) => {
      this.handleInputType(this.INPUT_TYPES.TEXT, e);
      this.$continueBtn.trigger('mouseover');
    });

    $('.page-wrapper').on('change', `.${this.CLASS.SELECT_CELL}`, (e) => {
      this.handleInputType(this.INPUT_TYPES.SELECT, e);
      this.$continueBtn.trigger('mouseover');
    });

    $(`.page-link`).unbind('change').on('click', (e) => {
      this.currentPage = $(e.currentTarget).html();
      this.paginator.changePageLabel(this.currentPage);
      this.renderTableElements(this.currentPage);
    });
  }

  /**
   * Method init page filters
   */
  initFilters() {
    this.renderFilters();
  }

  /**
   * Method init students table methods
   */
  initStudentsTable() {
    this.initStudentsTableHelpers();

    this.students.forEach((item, index) => {
      this.studentHelper.setField(index, 'selected', false);
    });

    this.renderTableElements(this.currentPage);
    this.initTableListeners();
  }

  /**
   * Method init addition classes for table
   */
  initStudentsTableHelpers() {
    this.currentPage = 1;

    this.studentHelper.setStudents(this.students);
    this.paginator = new Paginator(this.students.length);

    this.paginator.init();
  }

  getFilters() {
    this.studentHelper = new StudentHelper();

    this.sendGetFacultyRequest();
    this.sendGetGroupRequest();
    this.sendGetYearRequest();
  }

  /**
   * Handle all changes in table
   * and update student model
   *
   * @param type
   * @param e
   */
  handleInputType(type, e) {
    const $item = $(e.currentTarget);
    const $studentRow = $item.closest(`.${this.CLASS.STUDENT_ROW}`);
    const fieldType = $item.data('field');

    let value = '';

    switch (type) {
      case this.INPUT_TYPES.TEXT:
        value = $item.find(`.${this.CLASS.TEXT_INPUT}`).val();
        this.updateStudentModel($studentRow, fieldType, value);
        break;
      case this.INPUT_TYPES.CHECKBOX:
        value = $item.find(`.${this.CLASS.CHECKBOX_INPUT}`).prop('checked');
        this.updateStudentModel($studentRow, fieldType, value);
        break;
      case this.INPUT_TYPES.SELECT:
        value = $item.find(`.${this.CLASS.SELECT_INPUT}`).val();
        this.studentHelper.setGroupNumber($studentRow.data('id'), value);
        break;
    }
  }

  /**
   * Update fields in student model
   *
   * @param $item
   * @param fieldType
   * @param value
   */
  updateStudentModel($item, fieldType, value) {
    this.studentHelper.setField($item.data('id'), fieldType, value);
  }

  /**
   * @param e
   * @param state
   */
  handleManageButtonsClick(e, state) {
    const $button = $(e.currentTarget);
    const fieldType = $button.data('field');
    const selectedStudents = this.getStudentsRowsByDataParam(`[data-field="selected"]`);

    this.students.forEach((student, index) => {
      if (student.selected) {
        this.$continueBtn.trigger('mouseover');
        student[fieldType] = state;
        this.students[index] = student;
      }
    });

    selectedStudents.forEach(($studentRow) => {
      this.changeCheckBoxState($studentRow, fieldType, state);
      this.updateStudentModel($studentRow, fieldType, state);
    });
  }

  handleFiltersApplyBtn() {
    const filterSelects = $(`.${this.CLASS.FILTERS_SELECTS} .${this.CLASS.SELECT_INPUT}`);
    let unselectedCount = 0;

    filterSelects.each((index, item) => {
      const $item = $(item);
      const value = $item.val();
      const field = $item.attr('id');

      if (value !== '-') {
        let url = `/person/student?${field[0]}=${value}`;
        this.sendGetStudentsRequest(url);
      } else {
        unselectedCount++;
      }
    });

    if (unselectedCount === 3) {
      this.$tableBody.empty();
      this.paginator.$paginator.empty();
    }
  }

  /**
   * @returns {Array}
   */
  getStudentsRowsByDataParam(dataParam = '') {
    let selectedStudents = [];
    const selectedCheckboxes = $(`.${this.CLASS.CHECKBOX_CELL}${dataParam} .${this.CLASS.CHECKBOX_INPUT}`);

    $(selectedCheckboxes).each((index, item) => {
      const $item = $(item);
      const $studentRow = $item.closest(`.${this.CLASS.STUDENT_ROW}`);

      if (dataParam) {
        if ($item.prop('checked')) {
          selectedStudents.push($studentRow);
        }
      } else {
        selectedStudents.push($studentRow);
      }
    });

    return selectedStudents;
  }

  changeCheckBoxState($item, buttonType, state) {
    const $checkboxCell = $item.find(`.${this.CLASS.CHECKBOX_CELL}[data-field="${buttonType}"]`);

    $checkboxCell
      .find(`.${this.CLASS.CHECKBOX_INPUT}`)
      .prop('checked', state);
  }

  toggleSelectedCheckboxes(e) {
    const $button = $(e.currentTarget);
    const state = $button.data('state');
    const fieldType = $button.data('field');
    const studentsRows = this.getStudentsRowsByDataParam();

    $button.toggleClass('text-success', state);

    studentsRows.forEach(($studentRow) => {
      this.students[$studentRow.data('id')].selected = state;
      this.changeCheckBoxState($studentRow, fieldType, state);
    });

    $button.data('state', !state);
  }

  renderFilters() {
    let html = '';

    this.$filtersBody.empty();

    let faculties = [];
    this.filters.faculty.forEach((faculty) => {
      faculties.push(faculty.name);
    });

    html += `
      ${this.renderSelectType('faculty', '-', faculties, 'col m-1')}
      ${this.renderSelectType('group', '-', this.filters.group, 'col m-1')}
      ${this.renderSelectType('year', '-', this.filters.year, 'col m-1')}
    `;

    this.$filtersBody.html(html);
  }

  renderTableElements(page) {
    let html = '';
    const fromTo = this.paginator.calculate(page);

    this.$tableBody.empty();
    this.students.forEach((student, index) => {
      if (index >= fromTo.from && index <= fromTo.to)
        html += `
          <tr data-id="${index}" class="${this.CLASS.STUDENT_ROW}">
              ${this.renderTableCell(this.INPUT_TYPES.CHECKBOX, this.CLASS.CHECKBOX_CELL, 'selected', student.selected)}
              ${this.renderTableCell(this.INPUT_TYPES.TEXT, this.CLASS.TEXT_CELL, 'surname', student.surname)}
              ${this.renderTableCell(this.INPUT_TYPES.TEXT, this.CLASS.TEXT_CELL, 'name', student.name)}
              ${this.renderTableCell(this.INPUT_TYPES.TEXT, this.CLASS.TEXT_CELL, 'patronymic', student.patronymic)}
              ${this.renderTableCell(this.INPUT_TYPES.TEXT, this.CLASS.TEXT_CELL, 'login', student.login)}
              ${this.renderTableCell(
                this.INPUT_TYPES.SELECT,
                this.CLASS.SELECT_CELL,
                'group', 
                this.studentHelper.getStudentGroupNumber(index), 
                this.studentHelper.getGroups()
              )}
              ${this.renderTableCell(this.INPUT_TYPES.CHECKBOX, this.CLASS.CHECKBOX_CELL, 'budget', student.budget)}
              ${this.renderTableCell(this.INPUT_TYPES.CHECKBOX, this.CLASS.CHECKBOX_CELL, 'imagine', student.imagine)}
              ${this.renderTableCell(this.INPUT_TYPES.CHECKBOX, this.CLASS.CHECKBOX_CELL, 'office', student.office)}
          </tr>   
        `;
    });

    this.$tableBody.html(html);
  }

  renderTableCell(type, cellClass, fieldName, selected, list = [], extClass = '') {
    let html = `<td data-field=${fieldName} class="border ${cellClass}">`;

    switch (type) {
      case this.INPUT_TYPES.SELECT:
        html += this.renderSelectType(fieldName, selected, list, extClass);
        break;
      case this.INPUT_TYPES.CHECKBOX:
        html += this.renderCheckBox(fieldName, selected, extClass);
        break;
      case this.INPUT_TYPES.TEXT:
        html += this.renderTextType(fieldName, selected, extClass);
        break;
    }

    html += `</td>`;

    return html;
  }

  renderSelectType(fieldName, selected, list = [], extClass = '') {
    let html = `
            <select name="${fieldName}" class="form-control ${extClass} select-input" id="${fieldName}">
                <option value="${selected}">${selected}</option>`;

    list.forEach((value) => {
      if (value !== selected) {
        html += `<option value="${value}">${value}</option>`;
      }
    });

    html += `</select>`;

    return html;
  }

  renderTextType(fieldName, value, extClass = '') {
    return `<input type="text" class="form-control ${extClass} text-input" size="15" value="${value}"/>`;
  }

  renderCheckBox(fieldName, state, extClass = '') {
    return `<input type="checkbox" class="form-control ${extClass} checkbox-input" ${state ? 'checked' : ''}/>`;
  }

  removeFieldForStudent(array, fieldName) {
    let filteredArray = [];

    array.forEach((item) => {
      let newItem = {};

      for (let name in item) {
        if (name !== fieldName) {
          Object.assign(newItem, item)
        }
      }

      filteredArray.push(newItem);
    });

    return filteredArray;
  }

  getSelectedStudents() {
    let selectedStudents = [];

    this.students.forEach((item) => {
      if (item.selected) {
        selectedStudents.push(item);
      }
    });

    return this.removeFieldForStudent(selectedStudents, 'selected');
  }

  /**
   * Send request to api for students
   * by selected filters
   */
  sendGetStudentsRequest(url) {
    $.ajax({
      url: url,
      type: 'get',
      contentType: 'application/json',
      dataType: 'json',
      success: (data) => {
        this.students = data;
        this.initStudentsTable();
        this.$makeArchiveBtn.trigger('mouseover');
      }
    });
  }

  /**
   * Send request to api with handled students
   */
  sendPostDataRequest(url, filename = null) {
    $.ajax({
      url: url,
      type: 'post',
      data: JSON.stringify(this.getSelectedStudents()),
      contentType: 'application/json',
      xhrFields: {
        responseType: 'blob'
      },
      success: function (data) {
        if (!filename) {
          return false;
        }

        let a = document.createElement('a');
        let url = window.URL.createObjectURL(data);
        a.href = url;
        a.download = filename;
        a.click();
        window.URL.revokeObjectURL(url);
      }
    });
  }

    sendGetFacultyRequest() {
        $.ajax({
            url: '/filter/faculty',
            type: 'get',
            data: {},
            async: false,
            contentType: 'application/json',
            dataType: 'json',
            success: (data) => {
                data.forEach((facultyInfo) => {
                    this.filters.faculty.push({
                        name: facultyInfo.name,
                        abbr: facultyInfo.abbr
                    });
                });
            }
        });
    }

    sendGetGroupRequest() {
        $.ajax({
            url: '/filter/group',
            type: 'get',
            data: {},
            async: false,
            contentType: 'application/json',
            dataType: 'json',
            success: (data) => {
                data.forEach((groupDTO) => {
                    this.filters.group.push(this.studentHelper.getGroupNumber(groupDTO))
                });
            }
        });
    }

    sendGetYearRequest() {
        $.ajax({
            url: '/filter/year',
            type: 'get',
            data: {},
            async: false,
            contentType: 'application/json',
            dataType: 'json',
            success: (data) => {
                this.filters.year = data;

                this.init();
            }
        });
    }
}
