rmdir dist /S /Q
"%JAVA_HOME%\bin\jlink.exe" --module-path .\out\artifacts\LioFileTransfer_jar\LioFileTransfer.jar;"C:\Program Files\Java\jdk-11.0.1\jmods";.\lib\gson-modules.jar --add-modules LioFileTransfer --output ./dist --compress 2
echo @echo off > ./dist/LioFileTransfer.bat
echo title LioFileTransfer Server >> ./dist/LioFileTransfer.bat
echo .\bin\java.exe -m LioFileTransfer/LioFileTransfer.Main >> ./dist/LioFileTransfer.bat
copy config.json dist /y
robocopy webDocs dist/webDocs /MIR
echo PAUSE >> ./dist/LioFileTransfer.bat