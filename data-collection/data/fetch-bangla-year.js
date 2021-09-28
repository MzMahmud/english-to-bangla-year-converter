Date.prototype.equals = function (other) {
    return this.getTime() === other.getTime();
}

Date.prototype.moveToNextDay = function () {
    this.setDate(this.getDate() + 1);
    return this;
}

const banglaDateMap = new Map();

async function getBanglaDate() {
    const date = new Date(2020, 0, 1);
    const endDate = new Date(2021, 0, 1);
    let year, month, day, getbangla = 1;
    while (!endDate.equals(date)) {
        year = date.getFullYear();
        month = date.getMonth() + 1;
        day = date.getDate();

        const data = { day, month, year, getbangla };
        date.moveToNextDay();
        const url = 'http://www.banglatext.com/ajax/bangladate.php';
        const response = await fetch(
            url,
            {
                method: 'post',
                body: new URLSearchParams(data)
            }
        );
        const json = await response.json();
        console.log(json);
        banglaDateMap.set(data, json);
    }
    console.log(banglaDateMap);
}

//getBanglaDate();