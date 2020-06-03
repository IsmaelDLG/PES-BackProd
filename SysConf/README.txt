Per veure els logs de startup y shutdown del wrapper run_server.sh s'ha d'utilitzar l'eina estandar del sistema
Introduir commanda

sudo journalctl --unit=myapp

Per veurel's en temps real

sudo journalctl -f -u purplepoint
