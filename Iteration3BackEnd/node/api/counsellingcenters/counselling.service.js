const pool = require("../../config/database");

module.exports = {
    getAllCounsellingCenters: (data, callBack) => {
        pool.query(
          `select counselling_name,latitude,longitude,address,contact_details,suburbortown from counsellingcenters`,
          [],
          (error, results, fields) => {
            if (error) {
              callBack(error);
            }
            return callBack(null, results);
          }
        );
      }
}