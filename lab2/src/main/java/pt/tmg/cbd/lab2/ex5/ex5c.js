prefix = function prefix() {
    var prefixes = db.phones.aggregate([
        {$group: {_id: "$components.prefix", totalPrefixes: {$sum: 1}}},
        {$project: {_id: 0, "prefix": "$_id", totalPrefixes: 1 }}
    ]);

    print(prefixes);
}