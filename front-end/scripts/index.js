const BING_API_KEY = "Atln3dXDjP_wk0q4Ba52kPUzFGpwtp0TPTIGCfhn0MAR9vLxeohAemnWmigTyyk5";

var sampleURL =
    "https://dev.virtualearth.net/REST/v1/Locations?addressLine=201%20Mullica%20Hill%20Road&countryregion=US&adminDistrict=US&locality=Glassboro&postalcode=08062&maxRes=1&key=Atln3dXDjP_wk0q4Ba52kPUzFGpwtp0TPTIGCfhn0MAR9vLxeohAemnWmigTyyk5"

var routeSampleURL =
    "https://dev.virtualearth.net/REST/V1/Routes/Driving?&wp.0=39.7071113586426,%20-75.1110305786133&wp.1=201%20Rowan%20Blvd,%20Glassboro,%20NJ%2008028&key=Atln3dXDjP_wk0q4Ba52kPUzFGpwtp0TPTIGCfhn0MAR9vLxeohAemnWmigTyyk5"

function getCoordinates() {
    var startingAddr = document.getElementById("startingAddr").value;
    var endingAddr = document.getElementById("endingAddr").value;
    console.log(startingAddr);
    console.log(endingAddr);
}