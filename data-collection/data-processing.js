const fs = require('fs');

/*
    raw data sample
    [
        { "day": 19, "month": 10, "year": 2020, "getbangla": 1},
        [ 4, 7, 1427, 1, 19, 10, 2020, "Monday, 4 Kartrik 1427 Bongabdo", "সোমবার, ৪ কার্তিক ১৪২৭ বঙ্গাব্দ"]
    ]
 */

const year = 2020;
const banglaDayIndex = 0;
const banglaMonthCodeIndex = 1;
const banglaYearIndex = 2;
const dayCodeIndex = 3;
const textEnIndex = 7;
const textBnIndex = 8;

const rawdata = fs.readFileSync(`./data/raw-english-bangla-date-mapping-${year}.json`);
const dates = JSON.parse(rawdata);


const englishToBanglaDateMapping = [];
let monthNames = new Map();
let dayNames = new Map();
const banglaYearNames = { en: '', bn: '' };

for (const [dateEn, data] of dates) {
    parseEnglishToBanglaDateMapping(dateEn, data, englishToBanglaDateMapping);

    const parsedTextEn = parseNames(data[textEnIndex]);
    const parsedTextBn = parseNames(data[textBnIndex]);

    banglaYearNames.en = parsedTextEn.yearName;
    banglaYearNames.bn = parsedTextBn.yearName;

    parseMonthName(data, parsedTextEn, parsedTextBn, monthNames);
    parseDayNames(data, parsedTextEn, parsedTextBn, dayNames);

}


englishToBanglaDateMapping.sort((a, b) => dateObjCompare(a[0], b[0]));
storeAsJson(englishToBanglaDateMapping, `english-bangla-date-mapping-${year}.json`);

storeAsJson(banglaYearNames, 'bangla-year-names.json');

monthNames = [...monthNames.values()];
monthNames.sort((a, b) => a.index - b.index);
storeAsJson(monthNames, 'bangla-months.json');

dayNames = [...dayNames.values()];
dayNames.sort((a, b) => a.index - b.index);
storeAsJson(dayNames, 'day-of-the-week.json');


function parseMonthName(data, parsedTextEn, parsedTextBn, storageMap) {
    const monthName = {
        index: data[banglaMonthCodeIndex] - 1,
        nameEn: parsedTextEn.monthName,
        nameBn: parsedTextBn.monthName
    };
    storageMap.set(monthName.index, monthName);
}

function parseDayNames(data, parsedTextEn, parsedTextBn, storageMap) {
    const dayName = {
        index: data[dayCodeIndex],
        nameEn: parsedTextEn.dayName,
        nameBn: parsedTextBn.dayName
    };
    storageMap.set(dayName.index, dayName);
}

function parseNames(text) {
    const dayAndRest = text.split(',').map(str => str.trim());
    const dayName = dayAndRest[0];
    const dateArray = dayAndRest[1].split(' ');
    const monthName = dateArray[1];
    const yearName = dateArray[3];
    return { dayName, monthName, yearName };
}

function dateObjCompare(a, b) {
    if (a.year != b.year) return a.year - b.year;
    if (a.month != b.month) return a.month - b.month;
    return a.day - b.day;
}

function parseEnglishToBanglaDateMapping(dateEn, data, storageArray) {
    const englishDate = {
        day: dateEn.day,
        month: dateEn.month,
        year: dateEn.year
    };
    const banglaDate = {
        day: data[banglaDayIndex],
        month: data[banglaMonthCodeIndex],
        year: data[banglaYearIndex]
    };
    storageArray.push([englishDate, banglaDate]);
}

function storeAsJson(obj, jsonFileName) {
    console.log(obj);
    fs.writeFileSync(jsonFileName, JSON.stringify(obj));
}