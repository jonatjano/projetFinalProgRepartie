compile.bat
cd bin
jar cvmf ./../tempClient/MANIFEST.MF ./../Client_Bernouy_Dewilde_Selle.jar client
cd ..
cd bin
jar cvmf ./../tempServeur/MANIFEST.MF ./../Serveur_Bernouy_Dewilde_Selle.jar client server
cd ..
