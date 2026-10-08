# Yes, -X GET is redundant
curl.exe -i -X GET http://localhost:8080/api/v1/savings-accounts/1 `
  -H "Accept: application/json"
