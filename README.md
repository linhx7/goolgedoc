jdk 22
Giai nen file .credentials\drive-java-quickstart\StoredCredential.rar
giai nen file \src\main\resources\client_secret.rar

curl --location 'http://localhost:8080/api/drive/upload' \
--header 'Cookie: JSESSIONID=CE5FE7F0BEEED3044217667B60B3B628' \
--form 'file=@"/D:/test.docx"'


curl --location --request POST 'http://localhost:8080/api/drive/download/1iD1Gx0t1vigx9lFX13nDdSN9aZigLZM4yZxUyLKmUq8' \
--header 'Cookie: JSESSIONID=CE5FE7F0BEEED3044217667B60B3B628'



curl --location --request POST 'http://localhost:8080/api/drive/delete/1rmWydmGA_BrBfpd31J-1xmFhpZDMHvjE' \
--header 'Cookie: JSESSIONID=CE5FE7F0BEEED3044217667B60B3B628'