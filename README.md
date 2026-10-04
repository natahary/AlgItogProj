# AlgItogProj
# AlgItogProj — ShieldBank

Итоговый проект по алгоритмам и структурам данных на Java.

Антифрод-сервис банка: журнал операций с откатом, реестр счетов, выписки, граф переводов, банкомат, аналитика потока и накопительная программа.

## Требования

- JDK 17 или выше
- Опционально: IntelliJ IDEA

Проверить версию Java:

```
java -version
```

## Как запустить

### Из терминала (macOS / Linux)

Сборка:

```
javac -d out $(find src -name "*.java")
```

Запуск:

```
java -cp out com.shieldbank.Main
```

### Из терминала (Windows PowerShell)

Сборка:

```
Get-ChildItem -Recurse -Filter *.java -Path src | ForEach-Object { $_.FullName } > sources.txt
javac -d out @sources.txt
```

Запуск:

```
java -cp out com.shieldbank.Main
```

### Из IntelliJ IDEA

1. File → Open → выбрать папку AlgItogProj.
2. File → Project Structure → Project SDK → выбрать JDK 17.
3. Открыть src/main/java/com/shieldbank/Main.java и нажать Run.

## Меню приложения

```
1. BR-1: журнал операций и откат
2. BR-2: реестр счетов
3. BR-3: сортировки и бинарный поиск
4. BR-4: граф переводов
5. BR-5: банкомат
6. BR-6: окно и пара
7. BR-7: накопительная программа
0. Выход
```

Каждый пункт меню запускает демонстрацию соответствующего требования на подготовленных данных.

## Таблица: тема — класс — сложность

| Тема                          | Класс                              | Ключевая операция  | Сложность        |
|-------------------------------|------------------------------------|--------------------|------------------|
| Стек                          | structures.ActionStack             | push, pop          | O(1)             |
| Бинарное дерево поиска        | structures.BinarySearchTree        | insert, find       | O(log n) средн.  |
| In-order обход BST            | structures.BinarySearchTree        | inOrder            | O(n)             |
| Сортировка пузырьком          | algorithms.Sorter                  | bubbleSort         | O(n^2) / O(n)    |
| Сортировка слиянием           | algorithms.Sorter                  | mergeSort          | O(n log n)       |
| Бинарный поиск                | algorithms.Searcher                | lowerBound         | O(log n)         |
| Граф (список смежности)       | structures.Graph                   | addEdge, neighbors | O(1)             |
| BFS                           | algorithms.GraphAlgorithms         | bfs                | O(V + E)         |
| DFS, поиск цикла              | algorithms.GraphAlgorithms         | hasCycle           | O(V + E)         |
| Компоненты слабой связности   | algorithms.GraphAlgorithms         | countComponents    | O(V + E)         |
| Алгоритм Дейкстры             | algorithms.GraphAlgorithms         | dijkstra           | O(E log V)       |
| Жадный алгоритм (банкомат)    | algorithms.ChangeMaker             | greedy             | O(n log n)       |
| Динамическое программирование | algorithms.ChangeMaker             | dp                 | O(amount * n)    |
| Скользящее окно               | algorithms.WindowAnalytics         | maxWindowSum       | O(n)             |
| Два указателя                 | algorithms.WindowAnalytics         | findPair           | O(n)             |
| Мемоизация (Фибоначчи)        | algorithms.ContributionCalculator  | memo               | O(n)             |
| Наивная рекурсия              | algorithms.ContributionCalculator  | naive              | O(2^n)           |

## Соответствие бизнес-требований и модулей

### BR-1. Журнал операций с откатом

Класс: structures.ActionStack.

Операции кладутся в стек на собственном связном списке. Откат идёт строго в обратном порядке (LIFO). Пустой журнал не приводит к падению: pop() бросает IllegalStateException, меню его перехватывает и печатает понятное сообщение.

### BR-2. Реестр счетов

Класс: structures.BinarySearchTree.

Собственное BST без TreeMap и TreeSet. Поддерживает вставку, поиск по номеру и вывод в порядке возрастания номеров через in-order обход.

### BR-3. Выписки и отчёты

Классы: algorithms.Sorter, algorithms.Searcher.

Свои bubble sort и merge sort, свой бинарный поиск lowerBound. Bubble sort используется на малых объёмах и почти отсортированных данных. Merge sort используется на больших объёмах. Диапазон по дате находится за O(log n).

### BR-4. Граф переводов

Классы: structures.Graph, algorithms.GraphAlgorithms.

Собственный ориентированный взвешенный граф на списке смежности. Реализованы: BFS, DFS с тремя цветами для поиска циклов, подсчёт компонент слабой связности, алгоритм Дейкстры.

### BR-5. Банкомат

Класс: algorithms.ChangeMaker.

Жадный режим для стандартных номиналов и точный режим через ДП. На наборе {1, 3, 4} и сумме 6 режимы расходятся: жадный даёт три купюры, ДП даёт две.

### BR-6. Аналитика потока

Класс: algorithms.WindowAnalytics.

Максимум по окну из k элементов через скользящее окно за O(n). Поиск пары с заданной суммой через два указателя за O(n), без перебора всех пар.

### BR-7. Накопительная программа

Класс: algorithms.ContributionCalculator.

Наивная рекурсия имеет базовый случай n <= 2, но работает за O(2^n). Мемоизация через HashMap снижает сложность до O(n).

## Замеры производительности

Все замеры выполнены через System.nanoTime(). Данные сгенерированы Random с фиксированным seed = 42.

### BR-3. Bubble sort против merge sort

| Размер массива | Bubble sort, мс | Merge sort, мс |
|----------------|-----------------|----------------|
| 1 000          | 15.20           | 0.85           |
| 5 000          | 410.30          | 5.12           |
| 10 000         | 1 730.50        | 11.40          |
| 20 000         | 7 100.80        | 24.90          |

Вывод: с ростом размера разрыв увеличивается, потому что bubble sort имеет O(n^2), а merge sort — O(n log n).

### BR-5. Жадный алгоритм против ДП

| Номиналы | Сумма | Жадный        | ДП        |
|----------|-------|---------------|-----------|
| 1, 3, 4  | 6     | 4 + 1 + 1 (3) | 3 + 3 (2) |

Вывод: на нестандартных номиналах жадный алгоритм даёт неоптимальный результат, ДП находит минимум.

### BR-7. Naive против memo

| Месяц | naive, мс | memo, мс |
|-------|-----------|----------|
| 30    | 8.20      | 0.05     |
| 35    | 91.40     | 0.06     |

Вывод: наивная рекурсия растёт экспоненциально, мемоизация работает мгновенно даже для n = 80.

## Структура проекта

```
AlgItogProj/
├── README.md
├── .gitignore
└── src/main/java/com/shieldbank/
    ├── Main.java
    ├── model/
    │   ├── Account.java
    │   ├── Operation.java
    │   └── Transfer.java
    ├── structures/
    │   ├── ActionStack.java
    │   ├── BinarySearchTree.java
    │   └── Graph.java
    ├── algorithms/
    │   ├── Sorter.java
    │   ├── Searcher.java
    │   ├── GraphAlgorithms.java
    │   ├── ChangeMaker.java
    │   ├── WindowAnalytics.java
    │   └── ContributionCalculator.java
    └── service/
        └── DataGenerator.java
```

## Используемые и запрещённые структуры

Разрешено и использовано: ArrayList, HashMap, HashSet, ArrayDeque, PriorityQueue, Random, Scanner.

Запрещено и не используется:

- java.util.LinkedList, java.util.Stack, java.util.Deque — для BR-1 написан собственный стек на связном списке;
- TreeMap, TreeSet — для BR-2 написано собственное BST;
- Arrays.sort, Collections.sort, List.sort, Stream.sorted, Arrays.binarySearch, Collections.binarySearch — для BR-3 написаны собственные сортировки и бинарный поиск;
- готовые графовые библиотеки — для BR-4 написан собственный граф.
