var webcamStream;
var shouldScanQRCode = true;
var lastScanTime = 0;

$(document).ready(() => {
    $(".camera").hide();

    $(".start").click(() => {
        shouldScanQRCode = true;
        $(".camera").slideDown(1000);
        //yeu cau truy cap camera
        navigator.mediaDevices
            .getUserMedia({video: true})
            .then(function (stream) {
                webcamStream = stream;
                var webcam = document.getElementById("webcam");
                var canvas = document.getElementById("canvas");
                var ctx = canvas.getContext("2d");

                webcam.srcObject = stream;
                // kich hoat thẻ video
                webcam.addEventListener("play", function () {
                    canvas.width = webcam.videoWidth;
                    canvas.height = webcam.videoHeight;

                    function scanQRCode() {
                        //Lay time hien tai
                        var currentTime = Date.now();
                        //Kiem tra du thoi gian chờ giua cac lan quet
                        if (currentTime - lastScanTime < 400) {
                            // Đợi ít nhất 1 giây giữa các lần quét
                            requestAnimationFrame(scanQRCode);
                            return;
                        }
                        //Cap nhat thoi gian cuoi cung da quet
                        lastScanTime = currentTime;
                        //Check xem co tiep tuc quet ma qrcode
                        if (!shouldScanQRCode) {
                            return;
                        }
                        // Ve hinh anh tu video cua webcam len canvar, hinh anh de quet qrcode
                        ctx.drawImage(webcam, 0, 0, canvas.width, canvas.height);
                        var imageData = ctx.getImageData(
                            0,
                            0,
                            canvas.width,
                            canvas.height
                        );
                        // quet ma qr bang jsQR
                        var code = jsQR(
                            imageData.data,
                            imageData.width,
                            imageData.height
                        );
                        if (code) {
                            //qr code được đọc
                            try {
                                //qr code có value, phản hồi về api server
                                $.ajax({
                                    type: "POST",
                                    url: "http://localhost:8081/api/v1/staff/camera/check-qr-code",
                                    data: code.data,
                                    contentType: "application/json",
                                    success: function (reponse) {
                                        //   phia server phan hoi statuscode 200 thi dung camera va tat quet qrcode
                                        if (reponse.httpStatus === "OK") {
                                            shouldScanQRCode = false;
                                            if (webcamStream) {
                                                var tracks = webcamStream.getTracks();
                                                tracks.forEach(function (track) {
                                                    track.stop();
                                                });
                                            }
                                            $(".camera").slideUp(1000);
                                            return alert("Check Successfully")
                                        } else if (reponse.httpStatus === "NOT_FOUND") {
                                            return alert("Không tìm thấy phiếu đặt!")
                                        } else if (reponse.httpStatus === "ALREADY_REPORTED") {
                                            return alert("Phiếu đã được check in!", {
                                                title: null,
                                            })
                                        } else {
                                            return console.log("Server Error")
                                        }
                                    },
                                });
                            } catch {
                                console.log("The server has stopped working ");
                            }
                        }
                        //Len lich cho moi lan quét với toc do khung hinh cua camera => 60fps
                        requestAnimationFrame(scanQRCode);
                    }

                    //Tiep tuc xu li tu webcam
                    scanQRCode();
                });
            })
            .catch(function (error) {
                $(".camera").hide();
                alert("Vui lòng mở camera!");
            });
    });

    $(".close").click(() => {
        $(".camera").slideUp(1000);
        if (webcamStream) {
            var tracks = webcamStream.getTracks();
            tracks.forEach(function (track) {
                track.stop();
            });
        }
    });
});