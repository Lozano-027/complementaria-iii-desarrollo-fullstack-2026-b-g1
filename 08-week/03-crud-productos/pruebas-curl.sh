#!/usr/bin/env bash
# Ejecuta el CRUD completo y guarda la salida como evidencia.
# Uso: ./pruebas-curl.sh   (con la app corriendo en localhost:8080)
URL=http://localhost:8080/api/productos
OUT=evidencias/resultado-curl.txt
mkdir -p evidencias
{
echo "=== 1. CREAR (POST) ==="
curl -s -i -X POST $URL -H "Content-Type: application/json" -d @- <<'EOF'
{"nombre":"Mouse","descripcion":"Mouse inalámbrico","precio":45000,"stock":25}
EOF
echo -e "\n\n=== 2. LISTAR (GET) ==="
curl -s -i $URL
echo -e "\n\n=== 3. OBTENER (GET /1) ==="
curl -s -i $URL/1
echo -e "\n\n=== 4. ACTUALIZAR (PUT /1) ==="
curl -s -i -X PUT $URL/1 -H "Content-Type: application/json" -d @- <<'EOF'
{"nombre":"Mouse Gamer","descripcion":"RGB","precio":60000,"stock":20}
EOF
echo -e "\n\n=== 5. BORRAR (DELETE /1) ==="
curl -s -i -X DELETE $URL/1
echo -e "\n\n=== 6. OBTENER BORRADO (GET /1 -> 404) ==="
curl -s -i $URL/1
echo -e "\n\n=== 7. CREAR INVÁLIDO (POST -> 400) ==="
curl -s -i -X POST $URL -H "Content-Type: application/json" -d @- <<'EOF'
{"nombre":"","precio":-10,"stock":5}
EOF
echo
} | tee $OUT
echo "Evidencia guardada en $OUT"