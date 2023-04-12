#!/usr/bin/env bash
# Copyright (c) 2023 OceanBase
# flink-connector-oceanbase is licensed under Mulan PSL v2.
# You can use this software according to the terms and conditions of the Mulan PSL v2.
# You may obtain a copy of Mulan PSL v2 at:
#         http://license.coscl.org.cn/MulanPSL2
# THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
# EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
# MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
# See the Mulan PSL v2 for more details.

cd "$(dirname "$0")/../.." || exit
echo "Project Home: $PWD"

set -x
mvn -v

mvn clean install

sh tools/maven/shade/shade.sh
