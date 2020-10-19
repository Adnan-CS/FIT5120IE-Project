const pool = require("../../config/database");

module.exports = {
  getAllHospitals: (data, callBack) => {
        pool.query(
          `select hospital_name,address,suburb,postcode,latitude,longitude from hospitalservices`,
          [],
          (error, results, fields) => {
            if (error) {
              callBack(error);
            }
            return callBack(null, results);
          }
        );
      },
      getAllSevenEleven: (data, callBack) => {
        pool.query(
          `select store_name,address,suburb,postcode,phone,latitude,longitude from seveneleven`,
          [],
          (error, results, fields) => {
            if (error) {
              callBack(error);
            }
            return callBack(null, results);
          }
        );
      },
      getAllPoliceStations: (data, callBack) => {
        pool.query(
          `select station_address,suburb,postcode,phone,latitude,longitude from policeservices`,
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