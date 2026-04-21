/**
 * Student Management System - JavaScript
 */

document.addEventListener('DOMContentLoaded', function () {

    // ==========================================
    //  DELETE MODAL HANDLER
    // ==========================================
    const deleteModal = document.getElementById('deleteModal');
    if (deleteModal) {
        deleteModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const studentId = button.getAttribute('data-student-id');
            const studentName = button.getAttribute('data-student-name');

            const deleteNameSpan = document.getElementById('deleteStudentName');
            const deleteLink = document.getElementById('deleteLink');

            if (deleteNameSpan) {
                deleteNameSpan.textContent = studentName;
            }
            if (deleteLink) {
                deleteLink.href = '/students/' + studentId + '/delete';
            }
        });
    }

    // ==========================================
    //  AUTO-DISMISS ALERTS AFTER 5 SECONDS
    // ==========================================
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function (alert) {
        setTimeout(function () {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(alert);
            if (bsAlert) {
                bsAlert.close();
            }
        }, 5000);
    });

    // ==========================================
    //  PHONE NUMBER INPUT - ALLOW ONLY DIGITS
    // ==========================================
    const phoneInput = document.querySelector('input[name="phone"]');
    if (phoneInput) {
        phoneInput.addEventListener('input', function (e) {
            this.value = this.value.replace(/[^0-9]/g, '');
            if (this.value.length > 10) {
                this.value = this.value.slice(0, 10);
            }
        });
    }

    // ==========================================
    //  SET MAX DATE FOR DATE OF BIRTH
    // ==========================================
    const dobInput = document.querySelector('input[name="dateOfBirth"]');
    if (dobInput) {
        const today = new Date();
        const maxDate = new Date(today.getFullYear() - 15, today.getMonth(), today.getDate());
        const minDate = new Date(today.getFullYear() - 35, today.getMonth(), today.getDate());
        dobInput.setAttribute('max', maxDate.toISOString().split('T')[0]);
        dobInput.setAttribute('min', minDate.toISOString().split('T')[0]);
    }

    // ==========================================
    //  FORM VALIDATION VISUAL FEEDBACK
    // ==========================================
    const forms = document.querySelectorAll('#studentForm');
    forms.forEach(function (form) {
        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        });
    });

    // ==========================================
    //  TABLE ROW HOVER ANIMATION
    // ==========================================
    const tableRows = document.querySelectorAll('.student-row');
    tableRows.forEach(function (row) {
        row.style.cursor = 'default';
    });

    // ==========================================
    //  SEARCH INPUT CLEAR ON ESC
    // ==========================================
    const searchInput = document.querySelector('input[name="keyword"]');
    if (searchInput) {
        searchInput.addEventListener('keydown', function (e) {
            if (e.key === 'Escape') {
                this.value = '';
                this.form.submit();
            }
        });

        // Auto focus search
        if (searchInput.value) {
            searchInput.focus();
            searchInput.setSelectionRange(
                searchInput.value.length,
                searchInput.value.length
            );
        }
    }

    // ==========================================
    //  TOOLTIP INITIALIZATION
    // ==========================================
    const tooltipTriggerList = [].slice.call(
        document.querySelectorAll('[data-bs-toggle="tooltip"]')
    );
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // ==========================================
    //  ZIP CODE - ALLOW ONLY DIGITS
    // ==========================================
    const zipInput = document.querySelector('input[name="zipCode"]');
    if (zipInput) {
        zipInput.addEventListener('input', function () {
            this.value = this.value.replace(/[^0-9]/g, '');
            if (this.value.length > 6) {
                this.value = this.value.slice(0, 6);
            }
        });
    }

    console.log('Student Management System loaded successfully!');
});