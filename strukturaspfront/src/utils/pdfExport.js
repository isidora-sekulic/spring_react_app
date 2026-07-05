import pdfMake from "pdfmake/build/pdfmake";
import pdfFonts from "pdfmake/build/vfs_fonts";

pdfMake.vfs = pdfFonts.vfs;

export function exportStudyProgramPdf(report) {

    const studyProgram = report.studyProgram;
    const modules = report.modules;
    const subjects = report.subjects;
    const moduleSubjects = report.moduleSubjects;
    const electiveGroups = report.electiveGroups;

    function getYear(semester) {

        if (semester <= 2) return 1;

        if (semester <= 4) return 2;

        if (semester <= 6) return 3;

        return 4;

    }

    function getSubject(subjectId) {

        return subjects.find(
            s => s.subjectId === subjectId
        );

    }

    function getModuleSubjects(moduleId) {

        return moduleSubjects.filter(
            ms => ms.moduleId === moduleId
        );

    }

    function getElectiveGroups(moduleId) {

        return electiveGroups.filter(
            eg => eg.moduleId === moduleId
        );

    }

    const today = new Date().toLocaleDateString("sr-RS");

    const content = [

        {
            text: "Izveštaj studijskog programa",
            style: "title",
            alignment: "center"
        },

        {
            text: `Datum generisanja: ${today}`,
            style: "date",
            margin: [0, 10, 0, 15]
        },

        {
            text: `Naziv: ${studyProgram.name}`,
            style: "heading"
        },

        {
            text: "Opis:",
            bold: true,
            margin: [0, 10, 0, 5]
        },

        {
            text: studyProgram.description,
            margin: [0, 0, 0, 15]
        },

        {
            table: {

                widths: ["*", "*"],

                body: [

                    [
                        { text: "Trajanje", bold: true },
                        { text: "Ukupno ESPB", bold: true }
                    ],

                    [
                        `${studyProgram.durationYears} godine`,
                        `${studyProgram.totalEspb}`
                    ]

                ]

            },

            margin: [0, 0, 0, 20]

        }

    ];

    modules.forEach(module => {

        content.push({

            text: module.name,

            style: "moduleTitle"

        });

        content.push({

            text: module.description,

            margin: [0, 5, 0, 10]

        });

    const subjectsForModule = getModuleSubjects(module.moduleId);


       
            for (let year = 1; year <= 4; year++) {

            const yearSubjects = subjectsForModule.filter(
                ms => getYear(ms.semester) === year && !ms.elective
            );

            if (yearSubjects.length === 0) {
                continue;
            }

            content.push({

                text: `${year}. godina`,

                style: "yearTitle",

                margin: [0, 10, 0, 5]

            });

            let yearEspb = 0;

            const requiredTable = [

                [
                    { text: "Predmet", bold: true },
                    { text: "Semestar", bold: true },
                    { text: "ESPB", bold: true }
                ]

            ];

            yearSubjects.forEach(ms => {

                const subject = getSubject(ms.subjectId);

                yearEspb += subject.espb;

                requiredTable.push([

                    subject.name,

                    ms.semester.toString(),

                    subject.espb.toString()

                ]);

            });

            content.push({

                table: {

                    headerRows: 1,

                    widths: ["*", 70, 60],

                    body: requiredTable

                },

                margin: [0, 0, 0, 8]

            });

            content.push({

                text:
                    `Ukupno ESPB za obavezne predmete (${year}. godina): ${yearEspb}`,

                bold: true,

                margin: [0, 0, 0, 10]

            });

            const yearGroups = getElectiveGroups(module.moduleId)

                .filter(g => getYear(g.semester) === year);

            if (yearGroups.length > 0) {

                content.push({

                    text: "Izborne grupe",

                    style: "electiveTitle",

                    margin: [0, 5, 0, 5]

                });

                let electiveYearEspb = 0;

                yearGroups.forEach(group => {

                    const groupSubjects = subjects.filter(subject =>

                        group.subjectIds.includes(subject.subjectId)

                    );

                    const oneSubjectEspb =

                        groupSubjects.length > 0

                            ? groupSubjects[0].espb

                            : 0;

                    const groupEspb =

                        oneSubjectEspb * group.numberToChoose;

                    electiveYearEspb += groupEspb;

                    const subjectNames = groupSubjects

                        .map(s => s.name)

                        .join("\n");

                    content.push({

                        table: {

                            headerRows: 1,

                            widths: [120, "*", 60, 60],

                            body: [

                                [

                                    { text: "Izborna grupa", bold: true },

                                    { text: "Predmeti", bold: true },

                                    { text: "Bira se", bold: true },

                                    { text: "ESPB", bold: true }

                                ],

                                [

                                    group.name,

                                    subjectNames,

                                    group.numberToChoose.toString(),

                                    groupEspb.toString()

                                ]

                            ]

                        },

                        margin: [0, 0, 0, 8]

                    });

                });

    content.push({

        text:

            `Ukupno ESPB izbornih grupa (${year}. godina): ${electiveYearEspb}`,

        bold: true,

        margin: [0, 0, 0, 10]

    });

}

    }

    const moduleRequiredEspb = subjectsForModule
        .filter(ms => !ms.elective)
        .reduce((sum, ms) => {

            const subject = getSubject(ms.subjectId);

            return sum + (subject?.espb || 0);

        }, 0);

    const moduleElectiveEspb = getElectiveGroups(module.moduleId)
        .reduce((sum, group) => {

            const groupSubjects = subjects.filter(subject =>
                group.subjectIds.includes(subject.subjectId)
            );

            const oneSubjectEspb =
                groupSubjects.length > 0
                    ? groupSubjects[0].espb
                    : 0;

            return sum + oneSubjectEspb * group.numberToChoose;

    }, 0);

    content.push({

        text: `Ukupan ESPB modula: ${moduleRequiredEspb + moduleElectiveEspb}`,

        style: "moduleTotal",

        margin: [0, 5, 0, 20]

    });

    });

    content.push({

        text: `Ukupan ESPB studijskog programa: ${studyProgram.totalEspb}`,

        style: "programTotal"

    });

    const docDefinition = {

        pageSize: "A4",

        pageMargins: [40, 60, 40, 60],

        content,

        styles: {

            title: {

                fontSize: 22,

                bold: true

            },

            date: {

                fontSize: 10

            },

            heading: {

                fontSize: 18,

                bold: true

            },

            moduleTitle: {

                fontSize: 18,

                bold: true,

                margin: [0, 15, 0, 8]

            },

            yearTitle: {

                fontSize: 14,

                bold: true

            },

            electiveTitle: {

                fontSize: 13,

                bold: true

            },

            moduleTotal: {

                fontSize: 14,

                bold: true

            },

            programTotal: {

                fontSize: 18,

                bold: true,

                margin: [0, 20, 0, 0]

            }

        },

        defaultStyle: {

            fontSize: 11

        }

    };

    pdfMake.createPdf(docDefinition).download(`${studyProgram.name}.pdf`);

}