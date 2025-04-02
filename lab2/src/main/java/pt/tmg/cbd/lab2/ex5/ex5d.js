patterns = function patterns() {
    var numbers = db.phones.find({}, { "_id": 1 }).toArray();
    var elems = [];
    var unique_elems = [];

    numbers.forEach(function (number) {
        elems.push(number._id);
    });

    for (var el in elems) {
        const numStr = elems[el].toString();
        const reversedStr = numStr.split('').reverse().join('');

        if (numStr === reversedStr) {
            unique_elems.push(elems[el]);
        }
    }
    print(unique_elems)
}