
    const ctx = document.getElementById("myChart");

    new Chart(ctx, {
    type: "bar",
    data: {
    labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
    datasets: [
{
    label: "# of Votes",
    data: [12, 19, 3, 5, 2, 3],
    borderWidth: 1,
},
    ],
},
    options: {
    scales: {
    y: {
    beginAtZero: true,
},
},
},
});
    const ctx1 = document.getElementById("myAreaChart");

    new Chart(ctx1, {
    type: "line",
    data: {
    labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
    datasets: [
{
    label: "# of Votes",
    data: [12, 19, 3, 5, 2, 3],
    borderWidth: 1,
},
    ],
},
    options: {
    scales: {
    y: {
    beginAtZero: true,
},
},
},
});

    const ctx2 = document.getElementById("myPieChart");

    new Chart(ctx2, {
    type: "pie",
    data: {
    labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
    datasets: [
{
    label: "# of Votes",
    data: [12, 19, 3, 5, 2, 3],
    borderWidth: 1,
},
    ],
},
    options: {
    scales: {
    y: {
    beginAtZero: true,
},
},
},
});
    myArea;
    const ctx3 = document.getElementById("myArea");

    new Chart(ctx3, {
    type: "bar",
    data: {
    labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
    datasets: [
{
    label: "# of Votes",
    data: [12, 19, 3, 5, 2, 3],
    borderWidth: 1,
},
    ],
},
    options: {
    scales: {
    y: {
    beginAtZero: true,
},
},
},
});
