import moment from 'moment';

/**
 *
 *  INTERFACES
 *
 */

export interface INewAssignment {
    assignmentTitle: string;
    shortDescription: string;
    homeworkType: string | undefined;
    dueDate: string | undefined;
    courseName: string;
    sections: string[];
    maxGrade: string;
    maxAttempts: string;
    styleTests: ITests;
    unitTestName: string;
    unitTestTimeLimit: string;
    forbiddenStatements: string[];
    instructionsFile: Blob | undefined;
    exampleImplementation: Blob | undefined;
    testerClass: Blob | undefined;
}

export interface IAssignment {
    id: string;
    title: string;
    shortDescription: string;
    dueDate: string;
    homeworkType: string;
    maxAttempts: number;
    maxGrade: number;
    tests: ITester[];
    grade: number;
}

export interface ITestResult {
    testName: string;
    testType: string;
    state: string;
    testOutput: string[];
}

export interface IFeedback {
    grade: number;
    content: string;
    date: string;
}

export interface IAttempt {
    id: number;
    attemptFilename: string;
    note: string;
    testResults: ITestResult[];
    feedback: IFeedback;
}

export interface ISubmission { 
    id: string;
    attempts: IAttempt[]
};

export interface INotes {
    assignmentTitle: string;
    assignmentId: string;
    attemptNotes: {
        attempt: number;
        note: string;
    }[];
}

export interface IAnnouncement {
    author: string;
    date: string;
    content: string;
}

export interface IUserSelf {
    roleType: string;
    name: string;
    institution: string;
    instructor: string;
    assistant: string;
    courses: {
        course: string;
        section: string;
    }[];
}

export interface ITester {
    name: string;
    testType: string;
}

export type ITests = string[];

/**
 *
 *  METHODS
 *
 */

const sendAnnouncement = (announcement: string): void => {
    const formData = new FormData();
    formData.append('announcementContent', announcement);

    fetch('/api/instructor/announcements', {
        method: 'POST',
        body: formData,
    });
};

const createAssignment = ({
    assignmentTitle,
    shortDescription,
    homeworkType,
    dueDate,
    courseName,
    sections,
    maxGrade,
    maxAttempts,
    styleTests,
    unitTestName,
    unitTestTimeLimit,
    forbiddenStatements,
    instructionsFile,
    exampleImplementation,
    testerClass,
}: INewAssignment): void => {
    if (instructionsFile === undefined || exampleImplementation === undefined || testerClass === undefined) {
        return;
    }

    const formData = new FormData();

    formData.append('assignmentTitle', assignmentTitle);
    formData.append('shortDescription', shortDescription);
    formData.append('homeworkType', homeworkType || '');
    formData.append('dueDate', dueDate || '');
    formData.append('courseName', courseName);
    sections.forEach((section) => formData.append('sections', section));
    formData.append('maxGrade', maxGrade);
    formData.append('maxAttempts', maxAttempts);
    styleTests.forEach((test) => formData.append('styleTests', test));
    formData.append('unitTestName', unitTestName);
    formData.append('unitTestTimeLimit', unitTestTimeLimit);
    formData.append('forbiddenStatements', JSON.stringify(forbiddenStatements));
    formData.append('instructionsFile', instructionsFile);
    formData.append('exampleImplementation', exampleImplementation);
    formData.append('testerClass', testerClass);

    fetch('/api/assignments', {
        method: 'POST',
        body: formData,
    });
};

const addAttempt = (assignmentId: string, file: Blob | undefined): void => {
    if (file === undefined) return;

    const formData = new FormData();
    formData.append('attemptArchive', file);

    fetch(`/api/assignments/${assignmentId}/submissions`, {
        method: 'POST',
        body: formData,
    });
};

const giveFeedbackTo = (assignmentId: string, submissionId: string, attemptId: string, feedback: IFeedback): void => {

    const formData = new FormData();
    
    formData.append("grade", JSON.stringify(feedback.grade));
    formData.append("content", feedback.content);
    formData.append("date", feedback.date);
    
    fetch(`/api/assignments/${assignmentId}/submissions/${submissionId}/attempts/${attemptId}`, {
        method: 'POST',
        body: formData,
    });
    
};

const addNoteTo = (submissionId: string, attemptId: string, note: string): void => {

    const formData = new FormData();
    
    formData.append("content", note);
    
    fetch(`/api/assignments/123456/submissions/${submissionId}/attempts/${attemptId}/notes`, {
        method: 'POST',
        body: formData,
    });
    
};

const getAssignmentOf = async (assignmentId: string): Promise<IAssignment> => {
    const response = await fetch(`/api/assignments/${assignmentId}`);
    return response.json() as Promise<IAssignment>;
};

const getAllAssignments = async (): Promise<IAssignment[]> => {
    const response = await fetch(`/api/assignments/`);
    return response.json() as Promise<IAssignment[]>;
};

const getAllTests = async (): Promise<ITests> => {
    const response = await fetch(`/api/assignments/tests`);
    return response.json() as Promise<ITests>;
};

const getInstructionsFileOf = async (assignmentId: string, download: boolean): Promise<Blob> => {
    const response = await fetch(`/api/assignments/${assignmentId}/download`);
    const blob = response.blob();
    
    if (download) {
        helpers.sendDownload(blob, response.headers.get("Content-Disposition")?.match(/filename="(.+)"/)?.[1]);
    }
    
    return blob;
};

const getArchiveOfAttempt = async (assignmentId: string, submissionId: string, attemptId: string): Promise<Blob> => {
    const response = await fetch(`/api/assignments/${assignmentId}/submissions/${submissionId}/attempts/${attemptId}/download`);
    return response.blob();
};

const getSubmissionsOfAssignment = async (assignmentId: string): Promise<ISubmission[]> => {
    const response = await fetch(`/api/assignments/${assignmentId}/submissions/all`);
    
    return response.json() as Promise<ISubmission[]>;
    
};

const getSubmissionOfStudentFor = async (assignmentId: string): Promise<ISubmission | boolean> => {
    const response = await fetch(`/api/assignments/${assignmentId}/submissions`);
    
    if (response.ok) {
        return response.json() as Promise<ISubmission>;
    } else {
        return new Promise((resolve) => resolve(false));
    }
    
};

const getAttemptsOf = async (submissionId: string): Promise<IAttempt[]> => {
    const response = await fetch(`/api/assignments/123456/submissions/${submissionId}/attempts`);
    return response.json() as Promise<IAttempt[]>;
};

const getAttemptDetailsOf = async (submissionId: string, attemptId: string): Promise<IAttempt> => {
    const response = await fetch(`/api/assignments/123456/submissions/${submissionId}/attempts/${attemptId}`);
    return response.json() as Promise<IAttempt>;
};

const getAllNotes = async (): Promise<INotes[]> => {
    const response = await fetch(`/api/self/notes`);
    return response.json() as Promise<INotes[]>;
};

const getUserSelf = async (): Promise<IUserSelf> => {
    const response = await fetch(`/api/self`);
    return response.json() as Promise<IUserSelf>;
};

const getAllAnnouncements = async (): Promise<IAnnouncement[]> => {
    const response = await fetch(`/api/self/announcements`);
    return response.json() as Promise<IAnnouncement[]>;
};

const helpers = {
    isAssignmentComplete: (assignment: IAssignment): boolean => {
        return moment(Date.now()).isAfter(assignment.dueDate);
    },
    sendDownload: (promise: Promise<Blob>, filename: string | undefined): void => {
        promise.then((blob) => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');

            a.style.display = 'none';
            
            a.setAttribute("href", url);
            a.setAttribute("download", filename || "unnamed-file");
            
            document.body.appendChild(a);
            a.click();
            
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);
        });
    },
    stringFromDate: (timestamp: string): string => {
        return new Date(parseInt(timestamp)).toLocaleString();
    },
    isSubmissionValid: (response: boolean | ISubmission): response is ISubmission => {
        return response !== false && response !== true;
    }
};

const APITools = {
    sendAnnouncement,
    createAssignment,
    addAttempt,
    addNoteTo,
    getAllAnnouncements,
    getAllTests,
    getAttemptsOf,
    getArchiveOfAttempt,
    getAssignmentOf,
    getAllNotes,
    getInstructionsFileOf,
    getUserSelf,
    giveFeedbackTo,
    getAllAssignments,
    getAttemptDetailsOf,
    getSubmissionsOfAssignment,
    getSubmissionOfStudentFor,
    helpers,
};

export default APITools;
