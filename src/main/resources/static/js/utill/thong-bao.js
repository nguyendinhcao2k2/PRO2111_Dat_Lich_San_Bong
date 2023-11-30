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

            // Customize the toast content as needed5
            toast.innerHTML = `
                                <div style="border-radius:5px 5px 0px 0px" class="toast-header ${headerBackgroundColor}">
                                  <div class="m-2">
                                    <i class="far fa-futbol fa-lg" style="color: #ffffff"></i>
                                  </div>
                                  <strong style="color: #ffffff; font-size: 17px" class="me-auto">${headerText}</strong>
                                  <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                                </div>
                                <div class="toast-body" style="color: black; font-size: 14px;background-color: #ffffff;border-radius:0px 0px 12px 12px">
                                  ${bodyText}
                                </div>
                              `;

            // Append each toast to the inner container
            toastInnerContainer.appendChild(toast);

            // Thêm sự kiện click cho nút đóng của mỗi toast
            const closeButton = toast.querySelector(".btn-close");
            closeButton.addEventListener("click", function () {
                const toastInstance = bootstrap.Toast.getInstance(toast);
                toastInstance.hide(); // Ẩn toast khi nút đóng được click
            });

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

function createAndShowToast(backGround, headerText, bodyText) {
    const toastContainer = document.getElementById("toastContainer");
    const toastsContainer = document.getElementById("toasts");
    const maxToasts = 5;
    const toastsArray = [];
    let toastCounter = 1;

        toastCounter++;
        const toast = document.createElement("div");
        toast.className = "toast mb-2";
        toast.setAttribute("role", "alert");
        toast.setAttribute("aria-live", "assertive");
        toast.setAttribute("aria-atomic", "true");

        toast.innerHTML = `
          <div class="toast-header  ${backGround}" style="border-radius:5px 5px 0px 0px">
            <div class="mx-1">
              <i class="far fa-futbol fa-lg" style="color: #ffffff"></i>
            </div>
            <strong class="me-auto" style="color: #ffffff">${headerText}</strong>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
          </div>
          <div class="toast-body p-2" style="color: black; font-size: 14px;background-color: #ffffff;border-radius:0px 0px 5px 5px ">
            ${bodyText}
          </div>
        `;

        // thằng bố add thằng toast là con
        toastContainer.appendChild(toast);

        // hiển thị toast
        const toastInstance = new bootstrap.Toast(toast, {
            animation: true,
            autohide: true,
            delay: 5000,
        });

        // hiển thị ra toast
        toastInstance.show();

        toastsArray.forEach((toast) => {
            toast.addEventListener("hidden.bs.toast", function () {
                toast.remove();
                // Remove the container if there are no toasts left
                if (toastInnerContainer.childElementCount === 0) {
                    toastContainer.remove();
                }
            });
        });

        toastsArray.push(toast);

    // Thêm sự kiện click cho nút đóng của toast
    const closeButton = toast.querySelector(".btn-close");
    closeButton.addEventListener("click", function () {
        // Ẩn toast khi nút đóng được click
        toastInstance.hide();
    });
}