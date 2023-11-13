// Note :
// Sử dụng các thư viện js của bootstrap đổi các version mới nhất của bootstrap
// <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
// <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
// <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>

// bg-success: thành công(xanh), bg-danger: báo lỗi(đỏ), bg-warning: cảnh báo(vàng)

function createMultipleToasts(toastsArray) {

    // Create a new toast container
    const toastContainer = document.createElement("div");
    toastContainer.setAttribute("aria-live", "polite");
    toastContainer.setAttribute("aria-atomic", "true");
    toastContainer.className = "position-relative position-absolute top-0 end-0 p-3 position-fixed";
    toastContainer.style.zIndex = "9999";

    // Create an inner container for the toasts
    const toastInnerContainer = document.createElement("div");
    toastInnerContainer.className =
        "toast-container ";
    // Append the inner container to the main container
    toastContainer.appendChild(toastInnerContainer);

    // Add toasts to the inner container
    toastsArray.forEach(
        ([headerBackgroundColor, headerText, bodyText], index) => {
            const toast = document.createElement("div");
            toast.className = "toast";
            toast.setAttribute("role", "alert");
            toast.setAttribute("aria-live", "assertive");
            toast.setAttribute("aria-atomic", "true");

            // Customize the toast content as needed
            toast.innerHTML = `
                                <div class="toast-header ${headerBackgroundColor}">
                                  <div class="m-2">
                                    <i class="far fa-futbol fa-lg" style="color: #ffffff"></i>
                                  </div>
                                  <strong style="color: #ffffff; font-size: 17px" class="me-auto">${headerText}</strong>
                                  <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                                </div>
                                <div class="toast-body" style="color: black; font-size: 14px;background-color: #ffffff;border-radius: 12px ">
                                  ${bodyText}
                                </div>
                              `;

            // Append each toast to the inner container
            toastInnerContainer.appendChild(toast);

            // Initialize and show each toast with a delay
            setTimeout(() => {
                const toastInstance = new bootstrap.Toast(toast, {
                    animation: true,
                    autohide: true,
                    delay: 5000, // Set the autohide delay to 5000 milliseconds (5 seconds)
                });
                toastInstance.show();
            }, index * 1500); // Delay increases for each toast
        }
    );

    // Attach an event listener for the hidden event of each toast
    const toasts = toastInnerContainer.querySelectorAll(".toast");
    toasts.forEach((toast) => {
        toast.addEventListener("hidden.bs.toast", function () {
            toast.remove();
            // Remove the container if there are no toasts left
            if (toastInnerContainer.childElementCount === 0) {
                toastContainer.remove();
            }
        });
    });

    // Append the main container to the body
    document.body.appendChild(toastContainer);
}

// Example usage: Create and show multiple toasts
// const toastsArray = [
//     ["bg-success", "Error 1", "Description 1"],
//     ["bg-success", "Error 2", "Description 2"],
//     ["bg-success", "Error 3", "Description 3"],
//     ["bg-success", "Error 4", "Description 4"],
//     ["bg-success", "Error 5", "Description 5"],
//     ["bg-danger", "Combined Error", "Combined Description"],
//     ["bg-warning", "Warning 1", "Warning Description 1"],
//     ["bg-warning", "Warning 2", "Warning Description 2"],
//     ["bg-warning", "Warning 3", "Warning Description 3"],
//     ["bg-warning", "Warning 4", "Warning Description 4"],
//     ["bg-warning", "Warning 5", "Warning Description 5"],
// ];

// createMultipleToasts(toastsArray);