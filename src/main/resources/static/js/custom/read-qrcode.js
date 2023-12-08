var timCheckIn = 0;

$(document).ready(() => {
    findByThoiGianCheckIn();
    $(".camera").hide();

    let shouldScanQRCode = false;
    let webcamStream;
    let lastScanTime = 0;
    let videoDevices;

// Function to start the camera
    function startCamera() {
        navigator.mediaDevices.enumerateDevices()
            .then(function (devices) {
                videoDevices = devices.filter(device => device.kind === 'videoinput');

                const cameraList = document.getElementById('cameraList');
                cameraList.innerHTML = '';
                videoDevices.forEach((device, index) => {
                    const option = document.createElement('option');
                    option.value = index;
                    option.text = device.label || `Camera ${index + 1}`;
                    cameraList.appendChild(option);
                });

                $(".camera-selection").slideDown(1000);

                const defaultCameraIndex = 0;
                $("#cameraList").val(defaultCameraIndex);
                startCameraStream(defaultCameraIndex);
            })
            .catch(function (error) {
                console.error('Error enumerating devices:', error);
            });
    }

// Function to start the camera stream
    function startCameraStream(selectedCameraIndex) {
        shouldScanQRCode = true;
        $(".camera").slideDown(1000);

        if (webcamStream) {
            var tracks = webcamStream.getTracks();
            tracks.forEach(function (track) {
                track.stop();
            });
        }

        navigator.mediaDevices
            .getUserMedia({video: {deviceId: {exact: videoDevices[selectedCameraIndex].deviceId}}})
            .then(function (stream) {
                webcamStream = stream;
                var webcam = document.getElementById("webcam");
                var canvas = document.getElementById("canvas");
                var ctx = canvas.getContext("2d");

                webcam.srcObject = stream;

                webcam.addEventListener("loadedmetadata", function () {
                    // Set canvas dimensions based on webcam video dimensions
                    canvas.width = webcam.videoWidth / 2;
                    canvas.height = webcam.videoHeight / 2;

                    function scanQRCode() {
                        var currentTime = Date.now();

                        if (currentTime - lastScanTime < 100) {
                            requestAnimationFrame(scanQRCode);
                            return;
                        }

                        lastScanTime = currentTime;

                        if (!shouldScanQRCode) {
                            return;
                        }

                        ctx.drawImage(webcam, 0, 0, canvas.width, canvas.height);
                        var imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);

                        var code = jsQR(imageData.data, imageData.width, imageData.height);

                        if (code) {
                            try {
                                $.ajax({
                                    type: "POST",
                                    url: "http://localhost:8081/api/v1/staff/camera/check-qr-code",
                                    data: code.data,
                                    contentType: "application/json",
                                    success: function (response) {
                                        if (response.statusCode === "OK") {
                                            // shouldScanQRCode = false;
                                            // if (webcamStream) {
                                            //     var tracks = webcamStream.getTracks();
                                            //     tracks.forEach(function (track) {
                                            //         track.stop();
                                            //     });
                                            // }
                                            // $(".camera").slideUp(1000);
                                            // $(".start").show();
                                            // $(".camera-selection").slideUp(1000);
                                            return alert("Check-in thành công!");
                                        } else if (response.statusCode === "NOT_FOUND") {
                                            return alert("Không tìm thấy phiếu đặt!");
                                        }else if (response.statusCode === "PAYMENT_REQUIRED") {
                                            return alert("Phiếu đã được thanh toán rồi!");
                                        }
                                        else if (response.statusCode === "ALREADY_REPORTED") {
                                            return alert("Phiếu đã được check in!");
                                        } else if (response.statusCode === "LOCKED") {
                                            return alert("Bạn chưa đến thời gian check-in hoặc phiếu check-in đã quá hạn! Nếu không quá hạn bạn sẽ được check in trước thời gian bắt đầu trận đấu " + timCheckIn + " phút!");
                                        } else {
                                            return console.log("Server Error");
                                        }
                                    },
                                });
                            } catch {
                                console.log("The server has stopped working");
                            }
                        }

                        requestAnimationFrame(scanQRCode);
                    }

                    scanQRCode();
                });
            })
            .catch(function (error) {
                $(".camera").hide();
                alert("Vui lòng mở camera!");
            });
    }

// Click event for the ".start" button
    $(".start").click(() => {
        startCamera();
    });

// Change event for the camera list
    $(".camera-selection").on("change", "#cameraList", function () {
        const selectedCameraIndex = $(this).val();
        startCameraStream(selectedCameraIndex);
    });


    $(".close").click(() => {
        $(".camera-selection").slideUp(1000);
        if (webcamStream) {
            var tracks = webcamStream.getTracks();
            tracks.forEach(function (track) {
                track.stop();
            });
        }
    });
});

function findByThoiGianCheckIn() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/api/v1/staff/find-time-check-in",
        success: function (response) {
            timCheckIn = response.content;
        },
        error: function (error) {
            console.log(error);
        }
    })
}