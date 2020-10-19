const { getAllHospitals } = require("./hospital.service")
const { getAllSevenEleven } = require("./hospital.service")
const { getAllPoliceStations } = require("./hospital.service")

let nearestLocData=[];
module.exports = {
    getNearestHospital: (req, res) => {
      const body = req.body;
      getAllHospitals(body, (err, results) => {
        if (err) {
          console.log(err);
          return res.status(500).json({
            success: 0,
            message: "Database Error"
          });
        }
        calculateNearestCounselling(body,results,"hospital");
      });
      getAllPoliceStations(body, (err, results) => {
        if (err) {
          console.log(err);
          return res.status(500).json({
            success: 0,
            message: "Database Error"
          });
        }
        calculateNearestCounselling(body,results,"police");
      });      
      getAllSevenEleven(body, (err, results) => {
        if (err) {
          console.log(err);
          return res.status(500).json({
            success: 0,
            message: "Database Error"
          });
        }
        calculateNearestCounselling(body,results,"restaurant");
        return res.status(200).json(nearestLocData);
      });
    }
};
function calculateDistance(lat1, lon1, lat2, lon2) {
    var p = 0.017453292519943295;    // Math.PI / 180
    var c = Math.cos;
    var a = 0.5 - c((lat2 - lat1) * p)/2 + 
            c(lat1 * p) * c(lat2 * p) * 
            (1 - c((lon2 - lon1) * p))/2;
  
    return 12742 * Math.asin(Math.sqrt(a)); // 2 * R; R = 6371 km
  }
function calculateNearestCounselling(body,results,typeOfData){
    nearestCentre = {};
    count = 0;
    myResults = results;
    sevenEleven = 
    i = 0;
    for(val of results){
        distance = calculateDistance(val.latitude,val.longitude,body.latitude,body.longitude);
        myResults[i].calculatedDistance = distance;
        myResults[i].locationType = typeOfData;
        i++;
    }
    myResults.sort((dis1,dis2) => (dis1.calculatedDistance>dis2.calculatedDistance) ? 1:-1 );
    nearestLocData.push(myResults.slice(0,1)[0]); 
    return nearestLocData;
}